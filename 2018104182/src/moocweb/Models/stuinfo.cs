//------------------------------------------------------------------------------
// <auto-generated>
//     此代码已从模板生成。
//
//     手动更改此文件可能导致应用程序出现意外的行为。
//     如果重新生成代码，将覆盖对此文件的手动更改。
// </auto-generated>
//------------------------------------------------------------------------------

namespace moocweb.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class stuinfo
    {
        public string stu_num { get; set; }
        public string name { get; set; }
        public string pass { get; set; }
        public Nullable<bool> gender { get; set; }
        public string phonenum { get; set; }
        public string emailaddress { get; set; }
        public Nullable<long> college_id { get; set; }
        public string major { get; set; }
        public bool isdelete { get; set; }
        public long id { get; set; }
        public string lastIP { get; set; }
        public Nullable<System.DateTime> lastTime { get; set; }
    }
}
