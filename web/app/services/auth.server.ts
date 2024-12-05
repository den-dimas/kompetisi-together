import axios from "~/services/axios.server";

import { cookie } from "~/services/sessions.server";

export async function login(
  email: string,
  password: string,
  asParticipant: boolean
): Promise<{ token: string | null; id: string | null; error: any }> {
  try {
    const res = await axios.post(
      asParticipant ? "/participant/login" : "/penyelenggara/login",
      {
        email,
        password,
      }
    );

    const data = await res.data;

    return {
      token: data.token,
      id: asParticipant
        ? data.result.id_participant
        : data.result.id_penyelenggara,
      error: null,
    };
  } catch (error: any) {
    return { token: null, id: null, error: error.response.data.res_msg };
  }
}

export async function register(
  nama: string,
  email: string,
  password: string,
  asParticipant: boolean
): Promise<{ token: string | null; id: string | null; error: any }> {
  try {
    const res = await axios.post(
      asParticipant ? "/participant/register" : "/penyelenggara/register",
      {
        nama,
        email,
        password,
      }
    );

    const data = await res.data;

    return {
      token: data.token,
      id: asParticipant
        ? data.result.id_participant
        : data.result.id_penyelenggara,
      error: null,
    };
  } catch (error: any) {
    return { token: null, id: null, error: error.response.data.res_msg };
  }
}

export async function isLoggedIn(request: Request, getToken?: boolean) {
  const session = await cookie.getSession(request.headers.get("cookie"));

  const token = session.get("token");
  const role = session.get("role");
  const id = session.get("id");

  if (token) return getToken ? { token, id, role } : { role };

  return null;
}
