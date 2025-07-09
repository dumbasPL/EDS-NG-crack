LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE            := dex_helper_static
LOCAL_C_INCLUDES        := $(LOCAL_PATH)/include
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_SRC_FILES         := dex_helper.cc slicer/dex_format.cc slicer/dex_bytecode.cc slicer/sha1.cpp
LOCAL_SRC_FILES         += slicer/common.cc slicer/reader.cc slicer/dex_ir.cc slicer/dex_utf8.cc
LOCAL_EXPORT_LDLIBS     := -lz
include $(BUILD_STATIC_LIBRARY)
