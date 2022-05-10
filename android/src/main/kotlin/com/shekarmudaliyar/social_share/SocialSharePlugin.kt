package com.shekarmudaliyar.social_share

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.io.File

/** SocialSharePlugin */
class SocialSharePlugin(private val registrar: Registrar):  MethodCallHandler {

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "social_share")
            channel.setMethodCallHandler(SocialSharePlugin(registrar))
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "shareInstagramStory") {
            //share on instagram story
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("com.instagram.android")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")
        } else if (call.method == "shareFacebookStory") {
            //share on facebook story
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("com.facebook.katana")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")
        } else if (call.method == "shareOptions") {
            //native share options
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
            } else {
                intent.type = "text/plain";
            }

            intent.putExtra(Intent.EXTRA_TEXT, content)

            //create chooser intent to launch intent
            //source: "share" package by flutter (https://github.com/flutter/plugins/blob/master/packages/share/)
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)

            registrar.activeContext().startActivity(chooserIntent)
            result.success(true)

        } else if (call.method == "copyToClipboard") {
            //copies content onto the clipboard
            val content: String? = call.argument("content")
            val clipboard =registrar.context().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", content)
            clipboard.primaryClip = clip
            result.success(true)
        } else if (call.method == "shareWhatsapp") {
            //shares content on WhatsApp
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("com.whatsapp")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")

        } else if (call.method == "shareSms") {
            //shares content on sms
            val content: String? = call.argument("message")
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.type = "vnd.android-dir/mms-sms"
            intent.data = Uri.parse("sms:" )
            intent.putExtra("sms_body", content)
            try {
                registrar.activity().startActivity(intent)
                result.success("true")
            } catch (ex: ActivityNotFoundException) {
                result.success("false")
            }
        } else if (call.method == "shareTwitter") {
            //shares content on twitter
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("com.twitter.android")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")
        }
        else if (call.method == "shareTelegram") {
            //shares content on Telegram
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("org.telegram.messenger")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")
        }
        else if (call.method == "checkInstalledApps") {
            //check if the apps exists
            //creating a mutable map of apps
            var apps:MutableMap<String, Boolean> = mutableMapOf()
            //assigning package manager
            val pm: PackageManager =registrar.context().packageManager
            //get a list of installed apps.
            val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
            //intent to check sms app exists
            val intent = Intent(Intent.ACTION_SENDTO).addCategory(Intent.CATEGORY_DEFAULT)
            intent.type = "vnd.android-dir/mms-sms"
            intent.data = Uri.parse("sms:" )
            val resolvedActivities: List<ResolveInfo>  = pm.queryIntentActivities(intent, 0)
            //if sms app exists
            apps["sms"] = resolvedActivities.isNotEmpty()
            //if other app exists
            apps["instagram"] = packages.any  { it.packageName.toString().contentEquals("com.instagram.android") }
            apps["facebook"] = packages.any  { it.packageName.toString().contentEquals("com.facebook.katana") }
            apps["twitter"] = packages.any  { it.packageName.toString().contentEquals("com.twitter.android") }
            apps["linkedin"] = packages.any  { it.packageName.toString().contentEquals("com.linkedin.android") }
            apps["whatsapp"] = packages.any  { it.packageName.toString().contentEquals("com.whatsapp") }
            apps["telegram"] = packages.any  { it.packageName.toString().contentEquals("org.telegram.messenger") }

            result.success(apps)
        } else if (call.method == "shareTikTok") {
            //native share options
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("com.ss.android.ugc.trill")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")
            //create chooser intent to launch intent
            //source: "share" package by flutter (https://github.com/flutter/plugins/blob/master/packages/share/)
            //try {
                //registrar.activity().startActivity(intent)
               /// result.success("true")
            //} catch (ex: ActivityNotFoundException) {
                //result.success("false")
            //}

        }else if (call.method == "shareLinkedin") {
            //native share options
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("com.linkedin.android")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")

        }else if (call.method == "shareEmail") {
            //native share options
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("com.google.android.gm")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")
        }else if (call.method == "shareLine") {
            //native share options
            val content: String? = call.argument("content")
            val image: String? = call.argument("image")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            if (image!=null) {
                //check if  image is also provided
                val imagefile =  File(registrar.activeContext().cacheDir,image)
                val imageFileUri = FileProvider.getUriForFile(registrar.activeContext(), registrar.activeContext().applicationContext.packageName + ".com.shekarmudaliyar.social_share", imagefile)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,imageFileUri)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            } else {
                intent.type = "text/plain";
            }
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.setPackage("jp.naver.line.android")
            val chooserIntent: Intent = Intent.createChooser(intent, null /* dialog title optional */)
            registrar.activeContext().startActivity(chooserIntent)
            result.success("true")
        } else {
            result.notImplemented()
        }
    }
}
