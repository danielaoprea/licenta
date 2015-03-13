LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)


include D:\development\licenta\licenta\OpenCV-android-sdk\sdk\native\jni\OpenCV.mk

LOCAL_MODULE    := opencv_java
LOCAL_LDLIBS +=  -llog -ldl

include $(BUILD_SHARED_LIBRARY)
