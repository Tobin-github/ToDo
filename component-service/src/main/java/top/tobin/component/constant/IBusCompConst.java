package top.tobin.component.constant;

public interface IBusCompConst {
     /**
      * business module name
      */
     String ACCOUNTING_MODULE_BUS_NAME ="AccountingBusinessComp";
     String GALLERY_MODULE_BUS_NAME ="GalleryBusinessComp";
     String SCHEDULE_MODULE_BUS_NAME ="ScheduleBusinessComp";
     String MEDIA_MODULE_BUS_NAME ="MediaBusinessComp";
     String MY_MODULE_BUS_NAME ="MyBusinessComp";

     /**
      * business module imp
      */
     String ACCOUNTING_MODULE_BUS_IMP="top.tobin.accounting.comp.AccountingBusinessComp";
     String GALLERY_MODULE_BUS_IMP ="top.tobin.gallery.comp.GalleryBusinessComp";
     String SCHEDULE_MODULE_BUS_IMP ="top.tobin.schedule.comp.ScheduleBusinessComp";
     String MEDIA_MODULE_BUS_IMP ="top.tobin.media.comp.MediaBusinessComp";
     String MY_MODULE_BUS_IMP ="top.tobin.my.comp.MyBusinessComp";
}
