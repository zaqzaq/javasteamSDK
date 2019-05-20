#include <jni_md.h>
#include <jni.h>
#include <steam/isteamcontroller.h>
#include <iostream>

#include "steam_ISteamController.h"

#include <iostream>
#include <cstdint>
#include <steam/steam_api.h>
#include "SteamCallbackClass.h"

jclass getControllerHandleClass(JNIEnv* env) {
	jclass controllerHandleTClass = env->FindClass("steam/ControllerHandle_t");
	if (controllerHandleTClass == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerHandle_t");
		return nullptr;
	}
	return controllerHandleTClass;
}

jclass getControllerActionSetHandleClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerActionSetHandle_t");
	if (clazz == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerActionSetHandle_t");
		return nullptr;
	}
	return clazz;
}

jclass getControllerDigitalActionHandleClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerDigitalActionHandle_t");
	if (clazz == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerDigitalActionHandle_t");
		return nullptr;
	}
	return clazz;
}

jclass getControllerDigitalActionDataClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerDigitalActionData_t");
	if (clazz == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerDigitalActionData_t");
		return nullptr;
	}
	return clazz;
}

jclass getEControllerActionOriginClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/EControllerActionOrigin");
	if (clazz == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/EControllerActionOrigin");
		return nullptr;
	}
	return clazz;
}

jclass getESteamControllerPadClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ESteamControllerPad");
	if (clazz == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ESteamControllerPad");
		return nullptr;
	}
	return clazz;
}

jclass getControllerAnalogActionHandleClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerAnalogActionHandle_t");
	if (clazz == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerAnalogActionHandle_t");
		return nullptr;
	}
	return clazz;
}

jclass getControllerAnalogActionDataClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerAnalogActionData_t");
	if (clazz == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerAnalogActionData_t");
		return nullptr;
	}
	return clazz;
}

jobject createControllerHandle_t(JNIEnv * env, jlong controllerId) {
	jclass controllerHandleTClass = getControllerHandleClass(env);
	if (controllerHandleTClass == nullptr) {
		return nullptr;
	}
	jmethodID controllerHandleTClassConstructor = env->GetMethodID(controllerHandleTClass, "<init>", "(J)V");
	if (controllerHandleTClassConstructor == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerHandle_t constructor");
		return nullptr;
	}
	jobject newControllerHandle = env->NewObject(controllerHandleTClass, controllerHandleTClassConstructor, controllerId);
	return newControllerHandle;
}

jobject createControllerActionSetHandle_t(JNIEnv * env, jlong actionId) {
	jclass actionClazz = getControllerActionSetHandleClass(env);
	if (actionClazz == nullptr) {
		return nullptr;
	}
	jmethodID constructorMethod = env->GetMethodID(actionClazz, "<init>", "(J)V");
	if (constructorMethod == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerActionSetHandle_t constructor");
		return nullptr;
	}
	jobject newJObject = env->NewObject(actionClazz, constructorMethod, actionId);
	return newJObject;
}

jobject createDigitalActionHandle(JNIEnv* env, jlong id) {
	jclass clazz = getControllerDigitalActionHandleClass(env);
	if (clazz == nullptr) {
		return nullptr;
	}
	jmethodID constructorMethod = env->GetMethodID(clazz, "<init>", "(J)V");
	if (constructorMethod == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerDigitalActionHandle_t constructor");
		return nullptr;
	}
	jobject newJObject = env->NewObject(clazz, constructorMethod, id);
	return newJObject;
}

jobject createControllerAnalogActionHandle(JNIEnv* env, jlong id) {
	jclass clazz = getControllerAnalogActionHandleClass(env);
	if (clazz == nullptr) {
		return nullptr;
	}
	jmethodID constructorMethod = env->GetMethodID(clazz, "<init>", "(J)V");
	if (constructorMethod == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionHandle_t constructor");
		return nullptr;
	}
	jobject newJObject = env->NewObject(clazz, constructorMethod, id);
	return newJObject;
}

jobject createControllerDigitalActionDataJava(JNIEnv* env, ControllerDigitalActionData_t digitalActionData) {
	jclass clazz = getControllerDigitalActionDataClass(env);
	if (clazz == nullptr) {
		return nullptr;
	}
	jmethodID contructor = env->GetMethodID(clazz, "<init>", "()V");
	if (contructor == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerDigitalActionData_t constructor");
		return nullptr;
	}
	jobject object = env->NewObject(clazz, contructor);

	jfieldID bStateField = env->GetFieldID(clazz, "bState", "Z");
	if (bStateField == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerDigitalActionData_t bStateField");
		return nullptr;
	}
	jfieldID bActiveField = env->GetFieldID(clazz, "bActive", "Z");
	if (bActiveField == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerDigitalActionData_t bActiveField");
		return nullptr;
	}

	env->SetBooleanField(object, bStateField, (jboolean) digitalActionData.bState);
	env->SetBooleanField(object, bActiveField, (jboolean) digitalActionData.bActive);

	return object;
}

jobject createControllerAnalogActionData(JNIEnv* env, ControllerAnalogActionData_t analogActionData) {
	jclass ControllerAnalogActionDataClass = getControllerAnalogActionDataClass(env);
	if (ControllerAnalogActionDataClass == nullptr) {
		return nullptr;
	}
	jmethodID contructor = env->GetMethodID(ControllerAnalogActionDataClass, "<init>", "()V");
	if (contructor == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t constructor");
		return nullptr;
	}
	jobject object = env->NewObject(ControllerAnalogActionDataClass, contructor);

	jfieldID eModeField = env->GetFieldID(ControllerAnalogActionDataClass, "eMode", "Lsteam/EControllerSourceMode;");
	if (eModeField == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t eMode");
		return nullptr;
	}
	jfieldID xField = env->GetFieldID(ControllerAnalogActionDataClass, "x", "F");
	if (xField == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t x");
		return nullptr;
	}

	jfieldID yField = env->GetFieldID(ControllerAnalogActionDataClass, "y", "F");
	if (yField == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t y");
		return nullptr;
	}

	jfieldID bActiveField = env->GetFieldID(ControllerAnalogActionDataClass, "bActive", "Z");
	if (bActiveField == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t bActive");
		return nullptr;
	}

	env->SetBooleanField(object, bActiveField, (jboolean) analogActionData.bActive);

	// enum
	jclass EControllerSourceModeClass = env->FindClass("steam/EControllerSourceMode");
	if (EControllerSourceModeClass == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/EControllerSourceMode");
		return nullptr;
	}

	jfieldID noneField = env->GetStaticFieldID(EControllerSourceModeClass, "k_EControllerSourceMode_None", "Lsteam/EControllerSourceMode;");
	if (noneField == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field k_EControllerSourceMode_None");
		return nullptr;
	}

	jfieldID valuesFieldId = env->GetStaticFieldID(EControllerSourceModeClass, "VALUES", "[Lsteam/EControllerSourceMode;");
	if (valuesFieldId == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field VALUES on EControllerSourceMode");
		return nullptr;
	}
	auto enumValues = (jobjectArray) env->GetStaticObjectField(EControllerSourceModeClass, valuesFieldId);
	jobject enumValue = env->GetObjectArrayElement(enumValues, (jsize) analogActionData.eMode);
	env->SetObjectField(object, eModeField, enumValue);

	//
	env->SetFloatField(object, xField, (jfloat) analogActionData.x);
	env->SetFloatField(object, yField, analogActionData.y);

	return object;
}

jobjectArray createEControllerActionOrigins(JNIEnv* env, const EControllerActionOrigin *origins, int originCount) {
	jclass clazz = getEControllerActionOriginClass(env);
	if (clazz == nullptr) {
		return nullptr;
	}

	jfieldID noneField = env->GetStaticFieldID(clazz, "k_EControllerActionOrigin_None", "Lsteam/EControllerActionOrigin;");
	if (noneField == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field k_EControllerActionOrigin_None");
		return nullptr;
	}

	jfieldID valuesFieldId = env->GetStaticFieldID(clazz, "VALUES", "[Lsteam/EControllerActionOrigin;");
	if (valuesFieldId == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field VALUES on EControllerActionOrigin");
		return nullptr;
	}
	auto enumValues = (jobjectArray) env->GetStaticObjectField(clazz, valuesFieldId);

	jobject noneEnumValue = env->GetStaticObjectField(clazz, noneField);

	jobjectArray array = env->NewObjectArray(originCount, clazz, noneEnumValue);

	for (int i = 0; i < originCount; i++) {
		EControllerActionOrigin originCpp = origins[i];
		jobject originJava = env->GetObjectArrayElement(enumValues, (jsize) originCpp);
		env->SetObjectArrayElement(array, i, originJava);
	}

	return array;
}

ControllerHandle_t getControllerHandleFromJava(JNIEnv* env, jobject controllerHandleJava) {
	jclass controllerHandleClass = getControllerHandleClass(env);
	if (controllerHandleClass == nullptr) {
		return 0;
	}
	jmethodID controllerHandleMethod = env->GetMethodID(controllerHandleClass, "getControllerHandle", "()J");
	if (controllerHandleMethod == nullptr) {
		return 0;
	}

	jlong controllerHandleLong = env->CallLongMethod(controllerHandleJava, controllerHandleMethod);
	return (ControllerHandle_t) controllerHandleLong;
}

ControllerActionSetHandle_t getControllerActionSetHandleFromJava(JNIEnv* env, jobject controllerActionSetHandleJava) {
	jclass clazz = getControllerActionSetHandleClass(env);
	if (clazz == nullptr) {
		return 0;
	}
	jmethodID methodId = env->GetMethodID(clazz, "getControllerActionSetHandle", "()J");
	if (methodId == nullptr) {
		return 0;
	}

	jlong idAsLong = env->CallLongMethod(controllerActionSetHandleJava, methodId);
	return (ControllerActionSetHandle_t) idAsLong;
}

ControllerAnalogActionHandle_t getControllerAnalogActionHandleFromJava(JNIEnv* env, jobject ControllerAnalogActionHandleJava) {
	jclass clazz = getControllerAnalogActionHandleClass(env);
	if (clazz == nullptr) {
		return 0;
	}
	jmethodID methodId = env->GetMethodID(clazz, "getControllerAnalogActionHandle", "()J");
	if (methodId == nullptr) {
		return 0;
	}

	jlong idAsLong = env->CallLongMethod(ControllerAnalogActionHandleJava, methodId);
	return (ControllerAnalogActionHandle_t) idAsLong;
}

ControllerDigitalActionHandle_t getControllerDigitalActionHandleFromJava(JNIEnv* env, jobject digitalActionHandleJava) {
	jclass clazz = getControllerActionSetHandleClass(env);
	if (clazz == nullptr) {
		return 0;
	}
	jmethodID methodId = env->GetMethodID(clazz, "getControllerDigitalActionHandle", "()J");
	if (methodId == nullptr) {
		return 0;
	}

	jlong idAsLong = env->CallLongMethod(digitalActionHandleJava, methodId);
	return (ControllerHandle_t) idAsLong;
}

ESteamControllerPad getESteamControllerPad(JNIEnv* env, jobject enumJava) {
	jclass clazz = getESteamControllerPadClass(env);
	if (clazz == nullptr) {
		return k_ESteamControllerPad_Left;
	}

	jfieldID ordinalFieldId = env->GetFieldID(clazz, "ORDINAL", "I");
	if (ordinalFieldId == nullptr) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field ORDINAL of steam/ESteamControllerPad");
		return k_ESteamControllerPad_Left;
	}
	jint ordinal = env->GetIntField(enumJava, ordinalFieldId);
	return (ESteamControllerPad)ordinal;
}

/*
 * Class:     steam_ISteamController
 * Method:    nInit
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_steam_ISteamController_nInit(JNIEnv* env, jclass callingClass) {
	if (SteamController() == nullptr) {
		return false;
	}

	return SteamController()->Init();
}

/*
 * Class:     steam_ISteamController
 * Method:    nShutdown
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_steam_ISteamController_nShutdown(JNIEnv* env, jclass callingClass) {
	if (SteamController() == nullptr) {
		return false;
	}
	return SteamController()->Shutdown();
}

/*
 * Class:     steam_ISteamController
 * Method:    nRunFrame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_steam_ISteamController_nRunFrame(JNIEnv* env, jclass callingClass) {
	if (SteamController() == nullptr) {
		return;
	}
	SteamController()->RunFrame();
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetConnectedControllers
 * Signature: ()[Lsteam/ControllerHandle_t;
 */
JNIEXPORT jobjectArray JNICALL Java_steam_ISteamController_nGetConnectedControllers(JNIEnv* env, jclass callingClass) {
	ControllerHandle_t controllerHandles[STEAM_CONTROLLER_MAX_COUNT];
	int controllerHandleCount = SteamController()->GetConnectedControllers(controllerHandles);

	jclass controllerHandleClass = getControllerHandleClass(env);
	if (controllerHandleClass == nullptr) {
		return nullptr;
	}
	jobject blankControllerHandle = createControllerHandle_t(env, 0);
	if (blankControllerHandle == nullptr) {
		return nullptr;
	}
	jobjectArray controllerHandleArray = env->NewObjectArray(controllerHandleCount, controllerHandleClass, blankControllerHandle);

	for (int i = 0; i < controllerHandleCount; i++) {
		auto controllerHandleLong = (jlong) controllerHandles[i];
		jobject controllerHandle = createControllerHandle_t(env, controllerHandleLong);
		env->SetObjectArrayElement(controllerHandleArray, i, controllerHandle);
	}
	steamjni::SteamCallbackClass::log(env, "Java_steam_ISteamController_nGetConnectedControllers 05");
	return controllerHandleArray;
}

/*
 * Class:     steam_ISteamController
 * Method:    nShowBindingPanel
 * Signature: (Lsteam/ControllerHandle_t;)Z
 */
JNIEXPORT jboolean JNICALL Java_steam_ISteamController_nShowBindingPanel(JNIEnv* env, jclass callingClass, jobject controllerHandleJava) {
	if (SteamController() == nullptr) {
		return false;
	}

	jclass controllerHandleClass = getControllerHandleClass(env);
	if (controllerHandleClass == nullptr) {
		return false;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);

    return SteamController()->ShowBindingPanel(controllerHandle);
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetActionSetHandle
 * Signature: (Ljava/lang/String;)Lsteam/ControllerActionSetHandle_t;
 */
JNIEXPORT jobject JNICALL Java_steam_ISteamController_nGetActionSetHandle(JNIEnv* env, jclass callingClass, jstring pszActionSetName) {
	if (SteamController() == nullptr) {
		return nullptr;
	}

	jboolean isCopy = false;
	const char * leaderboardNameCpp = env->GetStringUTFChars(pszActionSetName, &isCopy);
	ControllerActionSetHandle_t actionSetHandle = SteamController()->GetActionSetHandle(leaderboardNameCpp);

	jobject actionSetHandleJava = createControllerActionSetHandle_t(env, (jlong) actionSetHandle);

	env->ReleaseStringUTFChars(pszActionSetName, leaderboardNameCpp);

	return actionSetHandleJava;
}

/*
 * Class:     steam_ISteamController
 * Method:    nActivateActionSet
 * Signature: (Lsteam/ControllerHandle_t;Lsteam/ControllerActionSetHandle_t;)V
 */
JNIEXPORT void JNICALL Java_steam_ISteamController_nActivateActionSet(JNIEnv* env, jclass callingClass, jobject controllerHandleJava,
		jobject actionSetHandleJava) {
	if (SteamController() == nullptr) {
		return;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ControllerActionSetHandle_t actionSetHandle = getControllerActionSetHandleFromJava(env, actionSetHandleJava);

	SteamController()->ActivateActionSet(controllerHandle, actionSetHandle);
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetCurrentActionSet
 * Signature: (Lsteam/ControllerHandle_t;)Lsteam/ControllerActionSetHandle_t;
 */
JNIEXPORT jobject JNICALL Java_steam_ISteamController_nGetCurrentActionSet(JNIEnv* env, jclass callingClass, jobject controllerHandleJava) {
	if (SteamController() == nullptr) {
		return nullptr;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ControllerActionSetHandle_t currentActionSet = SteamController()->GetCurrentActionSet(controllerHandle);
	return createControllerActionSetHandle_t(env, (jlong) currentActionSet);
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetDigitalActionHandle
 * Signature: (Ljava/lang/String;)Lsteam/ControllerDigitalActionHandle_t;
 */
JNIEXPORT jobject JNICALL Java_steam_ISteamController_nGetDigitalActionHandle(JNIEnv* env, jclass callingClass, jstring pszActionName) {
	if (SteamController() == nullptr) {
		return nullptr;
	}

	jboolean isCopy;
	const char* pszActionNameCpp = env->GetStringUTFChars(pszActionName, &isCopy);
	ControllerDigitalActionHandle_t digitalActionHandle = SteamController()->GetDigitalActionHandle(pszActionNameCpp);
	env->ReleaseStringUTFChars(pszActionName, pszActionNameCpp);

	return createDigitalActionHandle(env, digitalActionHandle);
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetDigitalActionData
 * Signature: (Lsteam/ControllerHandle_t;Lsteam/ControllerDigitalActionHandle_t;)Lsteam/ControllerDigitalActionData_t;
 */
JNIEXPORT jobject JNICALL Java_steam_ISteamController_nGetDigitalActionData(JNIEnv* env, jclass callingClass, jobject controllerHandleJava,
		jobject digitalActionHandleJava) {
	if (SteamController() == nullptr) {
		return nullptr;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ControllerDigitalActionHandle_t digitalActionHandle = getControllerDigitalActionHandleFromJava(env, digitalActionHandleJava);

	ControllerDigitalActionData_t digitalActionData = SteamController()->GetDigitalActionData(controllerHandle, digitalActionHandle);

	return createControllerDigitalActionDataJava(env, digitalActionData);
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetDigitalActionOrigins
 * Signature: (Lsteam/ControllerHandle_t;Lsteam/ControllerActionSetHandle_t;Lsteam/ControllerDigitalActionHandle_t;)[Lsteam/EControllerActionOrigin;
 */
JNIEXPORT jobjectArray JNICALL Java_steam_ISteamController_nGetDigitalActionOrigins(JNIEnv* env, jclass callingClass, jobject controllerHandleJava,
		jobject actionSetHandleJava, jobject digitalActionHandleJava) {
	if (SteamController() == nullptr) {
		return nullptr;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ControllerActionSetHandle_t actionSetHandle = getControllerActionSetHandleFromJava(env, actionSetHandleJava);
	ControllerDigitalActionHandle_t digitalActionHandle = getControllerDigitalActionHandleFromJava(env, digitalActionHandleJava);
	EControllerActionOrigin originsOut[STEAM_CONTROLLER_MAX_ORIGINS];
	int actionOriginCount = SteamController()->GetDigitalActionOrigins(controllerHandle, actionSetHandle, digitalActionHandle, originsOut);

	return createEControllerActionOrigins(env, originsOut, actionOriginCount);
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetAnalogActionHandle
 * Signature: (Ljava/lang/String;)Lsteam/ControllerAnalogActionHandle_t;
 */
JNIEXPORT jobject JNICALL Java_steam_ISteamController_nGetAnalogActionHandle(JNIEnv* env, jclass callingClass, jstring pszActionNameJava) {
	if (SteamController() == nullptr) {
		return nullptr;
	}

	jboolean isCopy;
	const char * pszActionName = env->GetStringUTFChars(pszActionNameJava, &isCopy);
	ControllerAnalogActionHandle_t analogActionHandle = SteamController()->GetAnalogActionHandle(pszActionName);
	env->ReleaseStringUTFChars(pszActionNameJava, pszActionName);

	return createControllerAnalogActionHandle(env, analogActionHandle);
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetAnalogActionData
 * Signature: (Lsteam/ControllerHandle_t;Lsteam/ControllerAnalogActionHandle_t;)Lsteam/ControllerAnalogActionData_t;
 */
JNIEXPORT jobject JNICALL Java_steam_ISteamController_nGetAnalogActionData(JNIEnv* env, jclass callingClass, jobject controllerHandleJava,
		jobject analogActionHandleJava) {
	if (SteamController() == nullptr) {
		return nullptr;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ControllerAnalogActionHandle_t analogActionHandle = getControllerAnalogActionHandleFromJava(env, analogActionHandleJava);

	ControllerAnalogActionData_t analogActionData = SteamController()->GetAnalogActionData(controllerHandle, analogActionHandle);

	return createControllerAnalogActionData(env, analogActionData);
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetAnalogActionOrigins
 * Signature: (Lsteam/ControllerHandle_t;Lsteam/ControllerActionSetHandle_t;Lsteam/ControllerAnalogActionHandle_t;)[Lsteam/EControllerActionOrigin;
 */
JNIEXPORT jobjectArray JNICALL Java_steam_ISteamController_nGetAnalogActionOrigins(JNIEnv* env, jclass callingClass, jobject controllerHandleJava,
		jobject actionSetHandleJava, jobject analogActionHandleJava) {
	if (SteamController() == nullptr) {
		return nullptr;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ControllerActionSetHandle_t actionSetHandle = getControllerActionSetHandleFromJava(env, actionSetHandleJava);
	ControllerAnalogActionHandle_t analogActionHandle = getControllerAnalogActionHandleFromJava(env, analogActionHandleJava);
	EControllerActionOrigin originsOut[STEAM_CONTROLLER_MAX_ORIGINS];
	int originCount = SteamController()->GetAnalogActionOrigins(controllerHandle, actionSetHandle, analogActionHandle, originsOut);

	return createEControllerActionOrigins(env, originsOut, originCount);
}

/*
 * Class:     steam_ISteamController
 * Method:    nStopAnalogActionMomentum
 * Signature: (Lsteam/ControllerHandle_t;Lsteam/ControllerAnalogActionHandle_t;)V
 */
JNIEXPORT void JNICALL Java_steam_ISteamController_nStopAnalogActionMomentum(JNIEnv* env, jclass callingClass, jobject controllerHandleJava,
		jobject eActionJava) {
	if (SteamController() == nullptr) {
		return;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ControllerAnalogActionHandle_t eAction = getControllerAnalogActionHandleFromJava(env, eActionJava);
	SteamController()->StopAnalogActionMomentum(controllerHandle, eAction);
}

/*
 * Class:     steam_ISteamController
 * Method:    nTriggerHapticPulse
 * Signature: (Lsteam/ControllerHandle_t;Lsteam/ESteamControllerPad;I)V
 */
JNIEXPORT void JNICALL Java_steam_ISteamController_nTriggerHapticPulse(JNIEnv* env, jclass callingClass, jobject controllerHandleJava, jobject eTargetPadJava,
		jint usDurationMicroSec) {
	if (SteamController() == nullptr) {
		return;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ESteamControllerPad eTargetPad = getESteamControllerPad(env, eTargetPadJava);
	SteamController()->TriggerHapticPulse(controllerHandle, eTargetPad, (unsigned short int) usDurationMicroSec);
}

