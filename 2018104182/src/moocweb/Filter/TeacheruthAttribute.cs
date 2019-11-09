using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;
using System.Web.Security;

namespace moocweb.Filter
{
    public class TeacherAuthAttribute : ActionFilterAttribute
    {
        public override void OnActionExecuting(ActionExecutingContext filterContext) {
            var account = filterContext.HttpContext.Session["account"];
            var level = filterContext.HttpContext.Session["level"];
            var l = (int?)level;
            if (l <= 0||l==null) {
                filterContext.Result = new RedirectToRouteResult(new RouteValueDictionary(new { controller = "Login", action = "Login", area = string.Empty }));
            }
        }
    }
}