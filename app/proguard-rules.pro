# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
 #指定代码的压缩级别
    -optimizationpasses 5

    #包明不混合大小写
    -dontusemixedcaseclassnames

    #是否混淆第三方jar
    -dontskipnonpubliclibraryclasses

     #优化  不优化输入的类文件
    -dontoptimize

     #预校验
    -dontpreverify

     #混淆时是否记录日志
    -verbose

     # 混淆时所采用的算法
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

    #保护注解
    -keepattributes *Annotation*

    # 保持哪些类不被混淆
    -keep public class * extends android.app.Fragment
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class com.android.vending.licensing.ILicensingService
    #如果有引用v4包可以添加下面这行
    -keep public class * extends android.support.v4.app.Fragment



    #忽略警告
    -ignorewarning

    ##记录生成的日志数据,gradle build时在本项目根目录输出##

    #apk 包内所有 class 的内部结构
    -dump class_files.txt
    #未混淆的类和成员
    -printseeds seeds.txt
    #列出从 apk 中删除的代码
    -printusage unused.txt
    #混淆前后的映射
    -printmapping mapping.txt

    ########记录生成的日志数据，gradle build时 在本项目根目录输出-end######


    #####混淆保护自己项目的部分代码以及引用的第三方jar包library#######

    #-libraryjars libs/umeng-analytics-v5.2.4.jar

    #三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
    #-libraryjars libs/sdk-v1.0.0.jar
    #-libraryjars libs/look-v1.0.1.jar

    #如果不想混淆 keep 掉
    -keep class com.lippi.recorder.iirfilterdesigner.** {*; }

    #项目特殊处理代码

    #忽略警告
    -dontwarn com.lippi.recorder.utils**
    #保留一个完整的包
    -keep class com.lippi.recorder.utils.** {
        *;
     }

    -keep class  com.lippi.recorder.utils.AudioRecorder{*;}


    #如果引用了v4或者v7包
    -dontwarn android.support.**

    ####混淆保护自己项目的部分代码以及引用的第三方jar包library-end####

    -keep public class * extends android.view.View {
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
        public void set*(...);
    }

    #保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }

    #保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }

    #保持自定义控件类不被混淆
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }

    #保持 Parcelable 不被混淆
    -keep class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
    }

    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable

    #保持 Serializable 不被混淆并且enum 类也不被混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }

    #保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
    -keepclassmembers enum * {
      public static **[] values();
     public static ** valueOf(java.lang.String);
   }

    -keepclassmembers class * {
        public void *ButtonClicked(android.view.View);
    }

    #不混淆资源类
    -keepclassmembers class **.R$* {
        public static <fields>;
    }
        #避免混淆 异常, 内部类, 泛型 等
         -keepattributes Exceptions
        -keepattributes         InnerClasses
        -keepattributes Signature
       -keepattributes      Deprecated
       -keepattributes     SourceFile
     -keepattributes   LineNumberTable
      -keepattributes      *Annotation*
      -keepattributes      EnclosingMethod


    #gson

    # Application classes that will be serialized/deserialized over Gson
    -keep class com.google.gson.examples.android.model.** { *; }

    -keep class sun.misc.Unsafe { *; }
    -keep class com.google.gson.stream.** { *; }
    #混淆自己的javabean
    # 使用Gson时需要配置Gson的解析对象及变量都不混淆。不然Gson会找不到变量。
    # 将下面替换成自己的实体类
    -keep class com.betterda.betterdapay.javabean.** { *; }



    # Keep our interfaces so they can be used by other ProGuard rules.
    # See http://sourceforge.net/p/proguard/bugs/466/ fresco的混淆
    -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

    # Do not strip any method/class that is annotated with @DoNotStrip
    -keep @com.facebook.common.internal.DoNotStrip class *
    -keepclassmembers class * {
        @com.facebook.common.internal.DoNotStrip *;
    }

    # Keep native methods
    -keepclassmembers class * {
        native <methods>;
    }





    #民生银行 混淆
    -keep  public class com.unionpay.uppay.net.HttpConnection {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.net.HttpParameters {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.model.BankCardInfo {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.model.PAAInfo {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.model.ResponseInfo {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.model.PurchaseInfo {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.util.DeviceInfo {
    	public <methods>;
    }
    -keep  public class java.util.HashMap {
    	public <methods>;
    }
    -keep  public class java.lang.String {
    	public <methods>;
    }
    -keep  public class java.util.List {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.util.PayEngine {
    	public <methods>;
    	native <methods>;
    }
    #end

# 银联 start
-dontwarn com.unionpay.**
-keep class com.unionpay.** { *; }
# 银联 end




    #butterknife 混淆原则
    -keep class butterknife.** { *; }
    -dontwarn butterknife.internal.**
    -keep class **$$ViewBinder { *; }

    -keepclasseswithmembernames class * {
        @butterknife.* <fields>;
    }

    -keepclasseswithmembernames class * {
        @butterknife.* <methods>;
    }
    #end


   #极光推送 start
    -dontwarn cn.jpush.**
    -keep class cn.jpush.** { *; }
    #end

    #友盟

    -dontusemixedcaseclassnames
        -dontshrink
        -dontoptimize
        -dontwarn com.google.android.maps.**
        -dontwarn android.webkit.WebView
        -dontwarn com.umeng.**
        -dontwarn com.tencent.weibo.sdk.**
        -dontwarn com.facebook.**
        -keep public class javax.**
        -keep public class android.webkit.**
        -dontwarn android.support.v4.**
        -keep enum com.facebook.**
        -keepattributes Exceptions,InnerClasses,Signature
        -keepattributes *Annotation*
        -keepattributes SourceFile,LineNumberTable

        -keep public interface com.facebook.**
        -keep public interface com.tencent.**
        -keep public interface com.umeng.socialize.**
        -keep public interface com.umeng.socialize.sensor.**
        -keep public interface com.umeng.scrshot.**
        -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
        -keep public class com.umeng.socialize.* {*;}


        -keep class com.facebook.**
        -keep class com.facebook.** { *; }
        -keep class com.umeng.scrshot.**
        -keep public class com.tencent.** {*;}
        -keep class com.umeng.socialize.sensor.**
        -keep class com.umeng.socialize.handler.**
        -keep class com.umeng.socialize.handler.*
        -keep class com.umeng.weixin.handler.**
        -keep class com.umeng.weixin.handler.*
        -keep class com.umeng.qq.handler.**
        -keep class com.umeng.qq.handler.*
        -keep class UMMoreHandler{*;}
        -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
        -keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
        -keep class im.yixin.sdk.api.YXMessage {*;}
        -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
        -keep class com.tencent.mm.sdk.** {
         *;
        }
        -keep class com.tencent.mm.opensdk.** {
       *;
        }
        -dontwarn twitter4j.**
        -keep class twitter4j.** { *; }

        -keep class com.tencent.** {*;}
        -dontwarn com.tencent.**
        -keep public class com.umeng.com.umeng.soexample.R$*{
        public static final int *;
        }
        -keep public class com.linkedin.android.mobilesdk.R$*{
        public static final int *;
            }
        -keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
        }

        -keep class com.tencent.open.TDialog$*
        -keep class com.tencent.open.TDialog$* {*;}
        -keep class com.tencent.open.PKDialog
        -keep class com.tencent.open.PKDialog {*;}
        -keep class com.tencent.open.PKDialog$*
        -keep class com.tencent.open.PKDialog$* {*;}

        -keep class com.sina.** {*;}
        -dontwarn com.sina.**
        -keep class  com.alipay.share.sdk.** {
           *;
        }
        -keepnames class * implements android.os.Parcelable {
        public static final ** CREATOR;
        }

        -keep class com.linkedin.** { *; }
        -keepattributes Signature
        #友盟end



# OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
        #rxjava start
    -dontwarn sun.misc.**
    -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
    }

    -keepattributes *Annotation*
    -keepclassmembers class ** {
        @com.hwangjr.rxbus.annotation.Subscribe public *;
        @com.hwangjr.rxbus.annotation.Produce public *;
    }
#rxjava end
#微信
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}
#微信