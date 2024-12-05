import axios from "~/services/axios.server";

export async function getPenyelenggaraByID(id: string) {
  try {
    const res = await axios.get("/penyelenggara/" + id, {
      withCredentials: true,
    });

    if (!res) return "Failed to get data";

    const data = await res.data;

    return data;
  } catch (error: any) {
    return { error: error.response.data.res_msg };
  }
}
