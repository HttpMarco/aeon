#![allow(non_snake_case)]

macro_rules! j_instance {
    ($class:expr, $jni_env:expr) => {
        $jni_env.alloc_object($class).unwrap().into_raw()
    };
}

/*
 * section: Java_net_http_aeon_reflections_AeonReflections
 */
use jni::JNIEnv;
use jni::objects::{JClass, JObject, JString};
use jni::signature::Primitive;
use jni::sys::jobject;

#[no_mangle]
pub extern "system" fn Java_net_http_aeon_reflections_AeonReflections_allocate0(environment: JNIEnv, _: JClass, class: JClass) -> jobject {
    j_instance!(class, environment)
}