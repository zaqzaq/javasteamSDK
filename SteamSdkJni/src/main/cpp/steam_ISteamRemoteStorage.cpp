#include "steam_ISteamRemoteStorage.h"

#include "SteamCallbackClass.h"

#include <steam/steam_api.h>

#include <iostream>
#include <sstream>

#include <jni.h>


using steamjni::SteamCallbackClass;

JNIEXPORT jboolean JNICALL Java_steam_ISteamRemoteStorage_nFileWrite(JNIEnv * env, jclass javaClass, jstring pchString, jobject pvData){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nFileWrite: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean isCopy = false;
	const char * pchStringCpp = env->GetStringUTFChars(pchString, &isCopy);
	char * pvDataCpp = (char *)env->GetDirectBufferAddress(pvData);
	if(pvDataCpp == 0){
		SteamCallbackClass::throwException(env, "Failed to get address of direct byte buffer");
	}
	int pvDataCppSize = env->GetDirectBufferCapacity(pvData);

//	std::stringstream out;
//	out << "Going to try and write file `" << pchStringCpp << "' writing data `" << std::string(pvDataCpp, pvDataCppSize) << "'";
//	SteamCallbackClass::log(env, out.str().c_str());

	bool returnFromSteam = SteamRemoteStorage()->FileWrite(pchStringCpp, pvDataCpp, pvDataCppSize);

	// clean up
	env->ReleaseStringUTFChars(pchString, pchStringCpp);

	return returnFromSteam;
}

JNIEXPORT jint JNICALL Java_steam_ISteamRemoteStorage_nFileRead(JNIEnv * env, jclass javaClass, jstring pchString, jobject pvData){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nFileRead: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean isCopy = false;
	const char * pchStringCpp = env->GetStringUTFChars(pchString, &isCopy);
	char * pvDataCpp = (char *)env->GetDirectBufferAddress(pvData);
	if(pvDataCpp == 0){
		SteamCallbackClass::throwException(env, "Failed to get address of direct byte buffer");
	}
	int pvDataCppSize = env->GetDirectBufferCapacity(pvData);
	jint returnFromSteam = SteamRemoteStorage()->FileRead(pchStringCpp, pvDataCpp, pvDataCppSize);

	// clean up
	env->ReleaseStringUTFChars(pchString, pchStringCpp);

	return returnFromSteam;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamRemoteStorage_nFileForget(JNIEnv * env, jclass javaClass, jstring pchString){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nFileRead: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean isCopy = false;
	const char * pchStringCpp = env->GetStringUTFChars(pchString, &isCopy);
	bool returnFromSteam = SteamRemoteStorage()->FileForget(pchStringCpp);

	// clean up
	env->ReleaseStringUTFChars(pchString, pchStringCpp);

	return returnFromSteam;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamRemoteStorage_nFileDelete(JNIEnv * env, jclass javaClass, jstring pchString){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nFileRead: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean isCopy = false;
	const char * pchStringCpp = env->GetStringUTFChars(pchString, &isCopy);
	bool returnFromSteam = SteamRemoteStorage()->FileDelete(pchStringCpp);

	// clean up
	env->ReleaseStringUTFChars(pchString, pchStringCpp);

	return returnFromSteam;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamRemoteStorage_nFileExists(JNIEnv * env, jclass javaClass, jstring pchString){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nFileExists: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	if(pchString == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nFileExists: pchString cannot be null.");
		return false;
	}
	jboolean isCopy = false;
	const char * pchStringCpp = env->GetStringUTFChars(pchString, &isCopy);
	if(pchStringCpp == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nFileExists: Couldn't get UTFChars of pchString");
		return false;
	}
	bool returnFromSteam = SteamRemoteStorage()->FileExists(pchStringCpp);

	// clean up
	env->ReleaseStringUTFChars(pchString, pchStringCpp);
	return returnFromSteam;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamRemoteStorage_nFilePersisted(JNIEnv * env, jclass javaClass, jstring pchString){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nFilePersisted: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean isCopy = false;
	const char * pchStringCpp = env->GetStringUTFChars(pchString, &isCopy);
	bool returnFromSteam = SteamRemoteStorage()->FilePersisted(pchStringCpp);

	// clean up
	env->ReleaseStringUTFChars(pchString, pchStringCpp);

	return returnFromSteam;
}

JNIEXPORT jint JNICALL Java_steam_ISteamRemoteStorage_nGetFileSize(JNIEnv * env, jclass javaClass, jstring pchString){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nGetFileSize: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean isCopy = false;
	const char * pchStringCpp = env->GetStringUTFChars(pchString, &isCopy);
	jint returnFromSteam = SteamRemoteStorage()->GetFileSize(pchStringCpp);

	// clean up
	env->ReleaseStringUTFChars(pchString, pchStringCpp);

	return returnFromSteam;
}

JNIEXPORT jlong JNICALL Java_steam_ISteamRemoteStorage_nGetFileTimestamp(JNIEnv * env, jclass javaClass, jstring pchString){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nGetFileTimestamp: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean isCopy = false;
	const char * pchStringCpp = env->GetStringUTFChars(pchString, &isCopy);
	jlong returnFromSteam = SteamRemoteStorage()->GetFileTimestamp(pchStringCpp);

	// clean up
	env->ReleaseStringUTFChars(pchString, pchStringCpp);

	return returnFromSteam;
}

JNIEXPORT jint JNICALL Java_steam_ISteamRemoteStorage_nGetQuotaTotalBytes(JNIEnv * env, jclass javaClass){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nGetQuotaTotalBytes: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	uint64 totalBytes = 0;
	uint64 availBytes = 0;
	bool returnFromSteam = SteamRemoteStorage()->GetQuota(&totalBytes, &availBytes);
	if(returnFromSteam == false){
		totalBytes = 0;
		availBytes = 0;
	}

	return totalBytes;
}

JNIEXPORT jint JNICALL Java_steam_ISteamRemoteStorage_nGetQuotaAvailableBytes(JNIEnv * env, jclass javaClass){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nGetQuotaAvailableBytes: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	uint64 totalBytes = 0;
	uint64 availBytes = 0;
	bool returnFromSteam = SteamRemoteStorage()->GetQuota(&totalBytes, &availBytes);
	if(returnFromSteam == false){
		totalBytes = 0;
		availBytes = 0;
	}

	return availBytes;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamRemoteStorage_nIsCloudEnabledForAccount(JNIEnv * env, jclass javaClass){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nIsCloudEnabledForAccount: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean returnFromSteam = SteamRemoteStorage()->IsCloudEnabledForAccount();

	return returnFromSteam;
}

JNIEXPORT jboolean JNICALL Java_steam_ISteamRemoteStorage_nIsCloudEnabledForApp(JNIEnv * env, jclass javaClass){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nIsCloudEnabledForApp: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	jboolean returnFromSteam = SteamRemoteStorage()->IsCloudEnabledForApp();

	return returnFromSteam;
}

JNIEXPORT jint JNICALL Java_steam_ISteamRemoteStorage_nGetFileCount(JNIEnv * env, jclass callingClass){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nGetFileCount: Could not initialize SteamAPI. Steam must be running to call this method.");
		return false;
	}
	return SteamRemoteStorage()->GetFileCount();
}

JNIEXPORT jobject JNICALL Java_steam_ISteamRemoteStorage_nGetFileNameAndSize(JNIEnv * env, jclass callingClass, jint iFile){
	if(SteamRemoteStorage() == 0){
		SteamCallbackClass::throwException(env, "Java_steam_ISteamRemoteStorage_nGetFileNameAndSize: Could not initialize SteamAPI. Steam must be running to call this method.");
		return 0;
	}

	int32 pnFileSizeInBytes = 0;
	const char * fileName = SteamRemoteStorage()->GetFileNameAndSize(iFile, &pnFileSizeInBytes);

	if(fileName == 0){
		return 0;
	}

	jstring fileNameJava = env->NewStringUTF(fileName);
	jint fileSizeJava = pnFileSizeInBytes;

	jclass steamFileHolderClass = env->FindClass("steam/SteamFileNameAndSize");
	if(steamFileHolderClass == 0){
		SteamCallbackClass::throwException(env, "Couldn't find class steam/SteamFileNameAndSize");
		return 0;
	}
	jmethodID constructor = env->GetMethodID(steamFileHolderClass, "<init>", "(Ljava/lang/String;I)V");
	jobject fileHolder = env->NewObject(steamFileHolderClass, constructor, fileNameJava, fileSizeJava);

	return fileHolder;
}
