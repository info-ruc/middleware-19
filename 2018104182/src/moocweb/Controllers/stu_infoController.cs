using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using moocweb.Models;
using moocweb.Filter;
namespace moocweb.Controllers
{
    public class stu_infoController : Controller
    {
        private Entities db = new Entities();

        // GET: stu_info
        public ActionResult Index()
        {
            return View(db.stu_info.ToList());
        }

        // GET: stu_info/Details/5
        [BasicAuth]
        public ActionResult Details(long? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            stu_info stu_info = db.stu_info.Find(id);
            if (stu_info == null)
            {
                return HttpNotFound();
            }
            return View(stu_info);
        }

        // GET: stu_info/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: stu_info/Create
        // 为了防止“过多发布”攻击，请启用要绑定到的特定属性，有关 
        // 详细信息，请参阅 https://go.microsoft.com/fwlink/?LinkId=317598。
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id,stu_num,name,pass,gender,phonenum,emailaddress,college_id,major,isdelete")] stu_info stu_info)
        {
            if (ModelState.IsValid)
            {
                db.stu_info.Add(stu_info);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(stu_info);
        }

        // GET: stu_info/Edit/5
        public ActionResult Edit(long? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            stu_info stu_info = db.stu_info.Find(id);
            if (stu_info == null)
            {
                return HttpNotFound();
            }
            return View(stu_info);
        }

        // POST: stu_info/Edit/5
        // 为了防止“过多发布”攻击，请启用要绑定到的特定属性，有关 
        // 详细信息，请参阅 https://go.microsoft.com/fwlink/?LinkId=317598。
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id,stu_num,name,pass,gender,phonenum,emailaddress,college_id,major,isdelete")] stu_info stu_info)
        {
            if (ModelState.IsValid)
            {
                db.Entry(stu_info).State = System.Data.Entity.EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(stu_info);
        }

        // GET: stu_info/Delete/5
        public ActionResult Delete(long? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            stu_info stu_info = db.stu_info.Find(id);
            if (stu_info == null)
            {
                return HttpNotFound();
            }
            return View(stu_info);
        }

        // POST: stu_info/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(long id)
        {
            stu_info stu_info = db.stu_info.Find(id);
            db.stu_info.Remove(stu_info);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
