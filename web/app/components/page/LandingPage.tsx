import { landingFeatures } from "~/constants";
import illust from "../../../assets/authIllustration.svg";

export default function LandingPage() {
  return (
    <div id="landing-page" className="bg-white">
      <section
        id="hero"
        className="w-screen flex flex-1 flex-wrap px-12 py-24 gap-8"
      >
        <img src={illust} className="" />

        <div className="relative flex flex-1 flex-col items-end justify-center gap-4">
          <h1 className="text-6xl font-black">KOMPETISI TOGETHER</h1>

          <p className="font-medium text-xl text-end">
            <span className="font-black">Discover, host, and dominate</span>{" "}
            competitions like never before. Whether you're an{" "}
            <span className="font-bold bg-dark-purple text-white px-1 pb-1">
              organizer
            </span>{" "}
            creating the next big event or a{" "}
            <span className="font-bold bg-dark-purple text-white px-1 pb-1">
              competitor
            </span>{" "}
            finding your perfect match,{" "}
            <span className="font-bold">
              KOMPOR has the tools you need to ignite success.
            </span>
          </p>
        </div>
      </section>

      <section id="features" className="bg-dark-purple text-white py-16">
        <div className="max-w-6xl mx-auto px-4">
          <div className="text-center mb-12">
            <h1 className="text-4xl font-black mb-4">
              Features That{" "}
              <span className="ml-2 font-black tracking-tighter bg-white text-dark-purple px-2">
                DRIVE SUCCESS
              </span>
            </h1>
            <p className="text-xl max-w-3xl mx-auto">
              Everything you need to create, discover, and excel in
              competitions. Our platform provides powerful tools for both
              organizers and competitors.
            </p>
          </div>

          <div className="grid md:grid-cols-3 gap-8">
            {landingFeatures.map((feature, index) => (
              <div
                key={index}
                className="bg-light-purple bg-opacity-10 rounded-lg p-6 hover:shadow-lg transition-shadow duration-300"
              >
                <div className="text-3xl mb-4 bg-white w-fit bg-opacity-20 p-2 rounded-full">
                  {feature.icon}
                </div>
                <h3 className="text-xl font-bold mb-3">{feature.title}</h3>
                <p className="text-white text-justify text-lg">
                  {feature.description}
                </p>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}
