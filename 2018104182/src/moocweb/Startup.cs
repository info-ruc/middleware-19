using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(moocweb.Startup))]
namespace moocweb
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
