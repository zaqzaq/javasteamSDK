#include <jni_md.h>
#include <jni.h>
#include <steam/isteamcontroller.h>
#include <iostream>

#include "steam_ISteamController.h"

#include <iostream>
#include <stdint.h>
#include <steam/steam_api.h>
#include "SteamCallbackClass.h"

jclass getControllerHandleClass(JNIEnv* env) {
	jclass controllerHandleTClass = env->FindClass("steam/ControllerHandle_t");
	if (controllerHandleTClass == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerHandle_t");
		return 0;
	}
	return controllerHandleTClass;
}

jclass getControllerActionSetHandleClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerActionSetHandle_t");
	if (clazz == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerActionSetHandle_t");
		return 0;
	}
	return clazz;
}

jclass getControllerDigitalActionHandleClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerDigitalActionHandle_t");
	if (clazz == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerDigitalActionHandle_t");
		return 0;
	}
	return clazz;
}

jclass getControllerDigitalActionDataClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerDigitalActionData_t");
	if (clazz == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerDigitalActionData_t");
		return 0;
	}
	return clazz;
}

jclass getEControllerActionOriginClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/EControllerActionOrigin");
	if (clazz == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/EControllerActionOrigin");
		return 0;
	}
	return clazz;
}

jclass getESteamControllerPadClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ESteamControllerPad");
	if (clazz == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ESteamControllerPad");
		return 0;
	}
	return clazz;
}

jclass getControllerAnalogActionHandleClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerAnalogActionHandle_t");
	if (clazz == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerAnalogActionHandle_t");
		return 0;
	}
	return clazz;
}

jclass getControllerAnalogActionDataClass(JNIEnv* env) {
	jclass clazz = env->FindClass("steam/ControllerAnalogActionData_t");
	if (clazz == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/ControllerAnalogActionData_t");
		return 0;
	}
	return clazz;
}

jobject createControllerHandle_t(JNIEnv * env, jlong controllerId) {
	jclass controllerHandleTClass = getControllerHandleClass(env);
	if (controllerHandleTClass == 0) {
		return 0;
	}
	jmethodID controllerHandleTClassConstructor = env->GetMethodID(controllerHandleTClass, "<init>", "(J)V");
	if (controllerHandleTClassConstructor == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerHandle_t constructor");
		return 0;
	}
	jobject newControllerHandle = env->NewObject(controllerHandleTClass, controllerHandleTClassConstructor, controllerId);
	return newControllerHandle;
}

jobject createControllerActionSetHandle_t(JNIEnv * env, jlong actionId) {
	jclass actionClazz = getControllerActionSetHandleClass(env);
	if (actionClazz == 0) {
		return 0;
	}
	jmethodID constructorMethod = env->GetMethodID(actionClazz, "<init>", "(J)V");
	if (constructorMethod == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerActionSetHandle_t constructor");
		return 0;
	}
	jobject newJObject = env->NewObject(actionClazz, constructorMethod, actionId);
	return newJObject;
}

jobject createDigitalActionHandle(JNIEnv* env, jlong id) {
	jclass clazz = getControllerDigitalActionHandleClass(env);
	if (clazz == 0) {
		return 0;
	}
	jmethodID constructorMethod = env->GetMethodID(clazz, "<init>", "(J)V");
	if (constructorMethod == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerDigitalActionHandle_t constructor");
		return 0;
	}
	jobject newJObject = env->NewObject(clazz, constructorMethod, id);
	return newJObject;
}

jobject createControllerAnalogActionHandle(JNIEnv* env, jlong id) {
	jclass clazz = getControllerAnalogActionHandleClass(env);
	if (clazz == 0) {
		return 0;
	}
	jmethodID constructorMethod = env->GetMethodID(clazz, "<init>", "(J)V");
	if (constructorMethod == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionHandle_t constructor");
		return 0;
	}
	jobject newJObject = env->NewObject(clazz, constructorMethod, id);
	return newJObject;
}

jobject createControllerDigitalActionDataJava(JNIEnv* env, ControllerDigitalActionData_t digitalActionData) {
	jclass clazz = getControllerDigitalActionDataClass(env);
	if (clazz == 0) {
		return 0;
	}
	jmethodID contructor = env->GetMethodID(clazz, "<init>", "()V");
	if (contructor == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerDigitalActionData_t constructor");
		return 0;
	}
	jobject object = env->NewObject(clazz, contructor);

	jfieldID bStateField = env->GetFieldID(clazz, "bState", "Z");
	if (bStateField == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerDigitalActionData_t bStateField");
		return 0;
	}
	jfieldID bActiveField = env->GetFieldID(clazz, "bActive", "Z");
	if (bActiveField == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerDigitalActionData_t bActiveField");
		return 0;
	}

	env->SetBooleanField(object, bStateField, (jboolean) digitalActionData.bState);
	env->SetBooleanField(object, bActiveField, (jboolean) digitalActionData.bActive);

	return object;
}

jobject createControllerAnalogActionData(JNIEnv* env, ControllerAnalogActionData_t analogActionData) {
	jclass ControllerAnalogActionDataClass = getControllerAnalogActionDataClass(env);
	if (ControllerAnalogActionDataClass == 0) {
		return 0;
	}
	jmethodID contructor = env->GetMethodID(ControllerAnalogActionDataClass, "<init>", "()V");
	if (contructor == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t constructor");
		return 0;
	}
	jobject object = env->NewObject(ControllerAnalogActionDataClass, contructor);

	jfieldID eModeField = env->GetFieldID(ControllerAnalogActionDataClass, "eMode", "Lsteam/EControllerSourceMode;");
	if (eModeField == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t eMode");
		return 0;
	}
	jfieldID xField = env->GetFieldID(ControllerAnalogActionDataClass, "x", "F");
	if (xField == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t x");
		return 0;
	}

	jfieldID yField = env->GetFieldID(ControllerAnalogActionDataClass, "y", "F");
	if (yField == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t y");
		return 0;
	}

	jfieldID bActiveField = env->GetFieldID(ControllerAnalogActionDataClass, "bActive", "Z");
	if (bActiveField == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find ControllerAnalogActionData_t bActive");
		return 0;
	}

	env->SetBooleanField(object, bActiveField, (jboolean) analogActionData.bActive);

	// enum
	jclass EControllerSourceModeClass = env->FindClass("steam/EControllerSourceMode");
	if (EControllerSourceModeClass == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to load java class steam/EControllerSourceMode");
		return 0;
	}

	jfieldID noneField = env->GetStaticFieldID(EControllerSourceModeClass, "k_EControllerSourceMode_None", "Lsteam/EControllerSourceMode;");
	if (noneField == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field k_EControllerSourceMode_None");
		return 0;
	}

	jfieldID valuesFieldId = env->GetStaticFieldID(EControllerSourceModeClass, "VALUES", "[Lsteam/EControllerSourceMode;");
	if (valuesFieldId == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field VALUES on EControllerSourceMode");
		return 0;
	}
	jobjectArray enumValues = (jobjectArray) env->GetStaticObjectField(EControllerSourceModeClass, valuesFieldId);
	jobject enumValue = env->GetObjectArrayElement(enumValues, (jsize) analogActionData.eMode);
	env->SetObjectField(object, eModeField, enumValue);

	//
	env->SetFloatField(object, xField, (jfloat) analogActionData.x);
	env->SetFloatField(object, yField, analogActionData.y);

	return object;
}

jobjectArray createEControllerActionOrigins(JNIEnv* env, EControllerActionOrigin *origins, int originCount) {
	jclass clazz = getEControllerActionOriginClass(env);
	if (clazz == 0) {
		return 0;
	}

	jfieldID noneField = env->GetStaticFieldID(clazz, "k_EControllerActionOrigin_None", "Lsteam/EControllerActionOrigin;");
	if (noneField == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field k_EControllerActionOrigin_None");
		return 0;
	}

	jfieldID valuesFieldId = env->GetStaticFieldID(clazz, "VALUES", "[Lsteam/EControllerActionOrigin;");
	if (valuesFieldId == 0) {
		steamjni::SteamCallbackClass::throwException(env, "Failed to find field VALUES on EControllerActionOrigin");
		return 0;
	}
	jobjectArray enumValues = (jobjectArray) env->GetStaticObjectField(clazz, valuesFieldId);

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
	if (controllerHandleClass == 0) {
		return 0;
	}
	jmethodID controllerHandleMethod = env->GetMethodID(controllerHandleClass, "getControllerHandle", "()J");
	if (controllerHandleMethod == 0) {
		return 0;
	}

	jlong controllerHandleLong = env->CallLongMethod(controllerHandleJava, controllerHandleMethod);
	return (ControllerHandle_t) controllerHandleLong;
}

ControllerActionSetHandle_t getControllerActionSetHandleFromJava(JNIEnv* env, jobject controllerActionSetHandleJava) {
	jclass clazz = getControllerActionSetHandleClass(env);
	if (clazz == 0) {
		return 0;
	}
	jmethodID methodId = env->GetMethodID(clazz, "getControllerActionSetHandle", "()J");
	if (methodId == 0) {
		return 0;
	}

	jlong idAsLong = env->CallLongMethod(controllerActionSetHandleJava, methodId);
	return (ControllerActionSetHandle_t) idAsLong;
}

ControllerAnalogActionHandle_t getControllerAnalogActionHandleFromJava(JNIEnv* env, jobject ControllerAnalogActionHandleJava) {
	jclass clazz = getControllerAnalogActionHandleClass(env);
	if (clazz == 0) {
		return 0;
	}
	jmethodID methodId = env->GetMethodID(clazz, "getControllerAnalogActionHandle", "()J");
	if (methodId == 0) {
		return 0;
	}

	jlong idAsLong = env->CallLongMethod(ControllerAnalogActionHandleJava, methodId);
	return (ControllerAnalogActionHandle_t) idAsLong;
}

ControllerDigitalActionHandle_t getControllerDigitalActionHandleFromJava(JNIEnv* env, jobject digitalActionHandleJava) {
	jclass clazz = getControllerActionSetHandleClass(env);
	if (clazz == 0) {
		return 0;
	}
	jmethodID methodId = env->GetMethodID(clazz, "getControllerDigitalActionHandle", "()J");
	if (methodId == 0) {
		return 0;
	}

	jlong idAsLong = env->CallLongMethod(digitalActionHandleJava, methodId);
	return (ControllerHandle_t) idAsLong;
}

ESteamControllerPad getESteamControllerPad(JNIEnv* env, jobject enumJava) {
	jclass clazz = getESteamControllerPadClass(env);
	if (clazz == 0) {
		return k_ESteamControllerPad_Left;
	}

	jfieldID ordinalFieldId = env->GetFieldID(clazz, "ORDINAL", "I");
	if (ordinalFieldId == 0) {
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
	if (SteamController() == 0) {
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
	if (SteamController() == 0) {
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
	if (SteamController() == 0) {
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
	if (controllerHandleClass == 0) {
		return 0;
	}
	jobject blankControllerHandle = createControllerHandle_t(env, 0);
	if (blankControllerHandle == 0) {
		return 0;
	}
	jobjectArray controllerHandleArray = env->NewObjectArray(controllerHandleCount, controllerHandleClass, blankControllerHandle);

	for (int i = 0; i < controllerHandleCount; i++) {
		jlong controllerHandleLong = (jlong) controllerHandles[i];
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
	if (SteamController() == 0) {
		return false;
	}

	jclass controllerHandleClass = getControllerHandleClass(env);
	if (controllerHandleClass == 0) {
		return false;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);

	bool showBindingPanel = false;
	showBindingPanel = SteamController()->ShowBindingPanel(controllerHandle);
	return showBindingPanel;
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetActionSetHandle
 * Signature: (Ljava/lang/String;)Lsteam/ControllerActionSetHandle_t;
 */
JNIEXPORT jobject JNICALL Java_steam_ISteamController_nGetActionSetHandle(JNIEnv* env, jclass callingClass, jstring pszActionSetName) {
	if (SteamController() == 0) {
		return 0;
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
	if (SteamController() == 0) {
		return;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ControllerActionSetHandle_t actionSetHandle = getControllerActionSetHandleFromJava(env, actionSetHandleJava);

	SteamController()->ActivateActionSet(controllerHandle, actionSetHandle);

	return;
}

/*
 * Class:     steam_ISteamController
 * Method:    nGetCurrentActionSet
 * Signature: (Lsteam/ControllerHandle_t;)Lsteam/ControllerActionSetHandle_t;
 */
JNIEXPORT jobject JNICALL Java_steam_ISteamController_nGetCurrentActionSet(JNIEnv* env, jclass callingClass, jobject controllerHandleJava) {
	if (SteamController() == 0) {
		return 0;
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
	if (SteamController() == 0) {
		return 0;
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
	if (SteamController() == 0) {
		return 0;
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
	if (SteamController() == 0) {
		return 0;
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
	if (SteamController() == 0) {
		return 0;
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
	if (SteamController() == 0) {
		return 0;
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
	if (SteamController() == 0) {
		return 0;
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
	if (SteamController() == 0) {
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
	if (SteamController() == 0) {
		return;
	}

	ControllerHandle_t controllerHandle = getControllerHandleFromJava(env, controllerHandleJava);
	ESteamControllerPad eTargetPad = getESteamControllerPad(env, eTargetPadJava);
	SteamController()->TriggerHapticPulse(controllerHandle, eTargetPad, (unsigned short int) usDurationMicroSec);
}

