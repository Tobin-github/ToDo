package top.tobin.web;

// Declare any non-default types here with import statements
import top.tobin.web.IWebAidlCallback;

interface IWebAidlInterface {
     /**
      * actionName: 不同的action， jsonParams: 需要根据不同的action从map中读取并依次转成其他
      */
      void handleWebAction(String actionName, String jsonParams, in IWebAidlCallback callback);
}
