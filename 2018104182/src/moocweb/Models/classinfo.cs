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
    
    public partial class classinfo
    {
        public long class_id { get; set; }
        public string class_name { get; set; }
        public string describe { get; set; }
        public Nullable<bool> isdelete { get; set; }
        public Nullable<System.DateTime> create_time { get; set; }
        public Nullable<long> tea_id { get; set; }
        public Nullable<int> s_count { get; set; }
        public string tea_name { get; set; }
    }
}