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
    
    public partial class qbase_chapter
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public qbase_chapter()
        {
            this.question = new HashSet<question>();
        }
    
        public long id { get; set; }
        public Nullable<long> setid { get; set; }
        public string chap_name { get; set; }
        public string describe { get; set; }
        public bool isdelete { get; set; }
    
        public virtual qbase_set qbase_set { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<question> question { get; set; }
        public virtual background background { get; set; }
    }
}
