/*
 * SteamGameServerCallbackClass.cpp
 *
 *      Author: karl
 */

#include "SteamGameServerCallbackClass.h"
#include "SteamCallbackClass.h"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

namespace steamjni {

SteamGameServerCallbackClass::SteamGameServerCallbackClass(JavaVM *jvm):
	m_CallbackSteamServersConnected( this, &SteamGameServerCallbackClass::OnSteamServersConnected ),
	m_CallbackSteamServersDisconnected( this, &SteamGameServerCallbackClass::OnSteamServersDisconnected ),
	m_CallbackPolicyResponse( this, &SteamGameServerCallbackClass::OnPolicyResponse ),
	m_CallbackGSClientApprove( this, &SteamGameServerCallbackClass::OnGSClientApprove ),
	m_CallbackGSClientDeny( this, &SteamGameServerCallbackClass::OnGSClientDeny ),
	m_CallbackGSClientKick( this, &SteamGameServerCallbackClass::OnGSClientKick ),
	m_CallbackP2PSessionConnectFail( this, &SteamGameServerCallbackClass::OnP2PSessionConnectFail ),
	m_CallbackP2PSessionRequest( this, &SteamGameServerCallbackClass::OnP2PSessionRequest )
{
	this->jvm = jvm;
}

SteamGameServerCallbackClass::~SteamGameServerCallbackClass() = default;

void SteamGameServerCallbackClass::log(const char* theString){
	steamjni::SteamCallbackClass::log(this->jvm, theString);
}

//-----------------------------------------------------------------------------
// Purpose: Take any action we need to on Steam notifying us we are now logged in
//-----------------------------------------------------------------------------
void SteamGameServerCallbackClass::OnSteamServersConnected( SteamServersConnected_t *pLogonSuccess ) {
	if(pLogonSuccess==nullptr){
		log("OnSteamServersConnected was passed null");
		return;
	}

	JNIEnv *env;
	this->jvm->AttachCurrentThread((void **) &env, nullptr);

	jclass iSteamGameServerClass = env->FindClass("steam/ISteamGameServer");
	jmethodID callBackMethodID = env->GetStaticMethodID(iSteamGameServerClass, "OnSteamServersConnected", "(I)V");
	if(callBackMethodID==nullptr){
		ostringstream errorString;
		errorString << "Could not find java method OnSteamServersConnected(I)V" << std::endl;
		log(&errorString.str()[0]);
		return;
	}
	env->CallStaticVoidMethod(iSteamGameServerClass, callBackMethodID, (jint)pLogonSuccess->k_iCallback);

	this->jvm->DetachCurrentThread();
}


//-----------------------------------------------------------------------------
// Purpose: Callback from Steam3 when logon is fully completed and VAC secure policy is set
//-----------------------------------------------------------------------------
void SteamGameServerCallbackClass::OnPolicyResponse( GSPolicyResponse_t *pPolicyResponse ){
	if(pPolicyResponse==nullptr){
		log("OnPolicyResponse was passed null");
		return;
	}
//	steamjni::SteamCallbackClass::log(this->jvm,"OnPolicyResponse");
//	ostringstream nativeStringBuffer;
//	nativeStringBuffer << "GSPolicyResponse_t result=" << pPolicyResponse;
//	steamjni::SteamCallbackClass::log(this->jvm,&nativeStringBuffer.str()[0]);
	JNIEnv *env;
	this->jvm->AttachCurrentThread((void **) &env, nullptr);

	jclass iSteamGameServerClass = env->FindClass("steam/ISteamGameServer");
	jmethodID callBackMethodID = env->GetStaticMethodID(iSteamGameServerClass, "OnPolicyResponse", "(IZ)V");
	if(callBackMethodID==nullptr){
		ostringstream errorString;
		errorString << "Could not find java method OnPolicyResponse(IZ)V" << std::endl;
		log(&errorString.str()[0]);
		return;
	}
	env->CallStaticVoidMethod(iSteamGameServerClass, callBackMethodID, (jint)pPolicyResponse->k_iCallback,
                              (jboolean) pPolicyResponse->m_bSecure != 0);

	this->jvm->DetachCurrentThread();
}


//-----------------------------------------------------------------------------
// Purpose: Called when we were previously logged into steam but get logged out
//-----------------------------------------------------------------------------
void SteamGameServerCallbackClass::OnSteamServersDisconnected( SteamServersDisconnected_t *pLoggedOff ){
	if(pLoggedOff==nullptr){
		log("OnSteamServersDisconnected was passed null");
		return;
	}
//	steamjni::SteamCallbackClass::log(this->jvm,"OnSteamServersDisconnected");
//	ostringstream nativeStringBuffer;
//	nativeStringBuffer << "SteamServersDisconnected_t result=" << pLoggedOff;
//	steamjni::SteamCallbackClass::log(this->jvm,&nativeStringBuffer.str()[0]);
	JNIEnv *env;
	this->jvm->AttachCurrentThread((void **) &env, nullptr);

	jclass iSteamGameServerClass = env->FindClass("steam/ISteamGameServer");
	jmethodID callBackMethodID = env->GetStaticMethodID(iSteamGameServerClass, "OnSteamServersDisconnected", "(II)V");
	if(callBackMethodID==nullptr){
		ostringstream errorString;
		errorString << "Could not find java method OnSteamServersDisconnected(II)V" << std::endl;
		log(&errorString.str()[0]);
		return;
	}
	env->CallStaticVoidMethod(iSteamGameServerClass, callBackMethodID, (jint)pLoggedOff->k_iCallback, (jint)pLoggedOff->m_eResult);

	this->jvm->DetachCurrentThread();
}

//-----------------------------------------------------------------------------
// Purpose: Tells us Steam3 (VAC and newer license checking) has accepted the user connection
//-----------------------------------------------------------------------------
void SteamGameServerCallbackClass::OnGSClientApprove( GSClientApprove_t *pGSClientApprove ){
	if(pGSClientApprove==nullptr){
		log("OnGSClientApprove was passed null");
		return;
	}
//	steamjni::SteamCallbackClass::log(this->jvm,"OnGSClientApprove");
//	ostringstream nativeStringBuffer;
//	steamjni::SteamCallbackClass::log(this->jvm, "=========================");
//	nativeStringBuffer << "GSClientApprove_t k_iCallback=" << pGSClientApprove->k_iCallback << " steamid=" << pGSClientApprove->m_SteamID.ConvertToUint64();
//	steamjni::SteamCallbackClass::log(this->jvm,&nativeStringBuffer.str()[0]);

	JNIEnv *env;
	this->jvm->AttachCurrentThread((void **) &env, nullptr);

	jclass iSteamGameServerClass = env->FindClass("steam/ISteamGameServer");
	jmethodID callBackMethodID = env->GetStaticMethodID(iSteamGameServerClass, "OnGSClientApprove", "(IJ)V");
	if(callBackMethodID==nullptr){
		ostringstream errorString;
		errorString << "Could not find java method OnGSClientApprove(IJ)V" << std::endl;
		log(&errorString.str()[0]);
		return;
	}
	env->CallStaticVoidMethod(iSteamGameServerClass, callBackMethodID, (jint)pGSClientApprove->k_iCallback, (jlong)pGSClientApprove->m_SteamID.ConvertToUint64());

	this->jvm->DetachCurrentThread();
}


//-----------------------------------------------------------------------------
// Purpose: Callback indicating that Steam has told us to deny a user connection
//-----------------------------------------------------------------------------
void SteamGameServerCallbackClass::OnGSClientDeny( GSClientDeny_t *pGSClientDeny ){
	if(pGSClientDeny==nullptr){
		log("OnGSClientDeny was passed null");
		return;
	}
//	steamjni::SteamCallbackClass::log(this->jvm,"OnGSClientDeny");
//	ostringstream nativeStringBuffer;
//	nativeStringBuffer << "GSClientDeny_t result=" << pGSClientDeny;
//	steamjni::SteamCallbackClass::log(this->jvm,&nativeStringBuffer.str()[0]);

	JNIEnv *env;
	this->jvm->AttachCurrentThread((void **) &env, nullptr);
	jstring optionalString = env->NewStringUTF(pGSClientDeny->m_rgchOptionalText);
	jclass iSteamGameServerClass = env->FindClass("steam/ISteamGameServer");
	jmethodID callBackMethodID = env->GetStaticMethodID(iSteamGameServerClass, "OnGSClientDeny", "(IILjava/lang/String;J)V");
	if(callBackMethodID==nullptr){
		ostringstream errorString;
		errorString << "Could not find java method OnGSClientDeny(IILjava/lang/String;J)V" << std::endl;
		log(&errorString.str()[0]);
		return;
	}
	env->CallStaticVoidMethod(iSteamGameServerClass, callBackMethodID, (jint)pGSClientDeny->k_iCallback, (jint)pGSClientDeny->m_eDenyReason, optionalString, (jlong)pGSClientDeny->m_SteamID.ConvertToUint64());

	this->jvm->DetachCurrentThread();
	jvm->DetachCurrentThread();
}


//-----------------------------------------------------------------------------
// Purpose: Steam is telling us to kick a client (they probably lost connection to 
// Steam and now VAC can't verify them...)
//-----------------------------------------------------------------------------
void SteamGameServerCallbackClass::OnGSClientKick( GSClientKick_t *pGSClientKick ){
	if(pGSClientKick==nullptr){
		log("OnGSClientKick was passed null");
		return;
	}
//	steamjni::SteamCallbackClass::log(this->jvm,"OnGSClientKick");
//	ostringstream nativeStringBuffer;
//	nativeStringBuffer << "GSClientKick_t result=" << pGSClientKick;
//	steamjni::SteamCallbackClass::log(this->jvm,&nativeStringBuffer.str()[0]);

	JNIEnv *env;
	this->jvm->AttachCurrentThread((void **) &env, nullptr);

	jclass iSteamGameServerClass = env->FindClass("steam/ISteamGameServer");
	jmethodID callBackMethodID = env->GetStaticMethodID(iSteamGameServerClass, "OnGSClientKick", "(IIJ)V");
	if(callBackMethodID==nullptr){
		ostringstream errorString;
		errorString << "Could not find java method OnGSClientKick(IIJ)V" << std::endl;
		log(&errorString.str()[0]);
		return;
	}
	env->CallStaticVoidMethod(iSteamGameServerClass, callBackMethodID, (jint)pGSClientKick->k_iCallback, (jint)pGSClientKick->m_eDenyReason, (jlong)pGSClientKick->m_SteamID.ConvertToUint64());

	this->jvm->DetachCurrentThread();
}

//-----------------------------------------------------------------------------
// Purpose: Handle clients connecting
//-----------------------------------------------------------------------------
void SteamGameServerCallbackClass::OnP2PSessionRequest( P2PSessionRequest_t *pCallback ){
	if(pCallback==nullptr){
		log("OnP2PSessionRequest was passed null");
		return;
	}
//	steamjni::SteamCallbackClass::log(this->jvm,"OnP2PSessionRequest");
//	ostringstream nativeStringBuffer;
//	nativeStringBuffer << "P2PSessionRequest_t result=" << pCallback;
//	steamjni::SteamCallbackClass::log(this->jvm,&nativeStringBuffer.str()[0]);

	JNIEnv *env;
	this->jvm->AttachCurrentThread((void **) &env, nullptr);

	jclass iSteamGameServerClass = env->FindClass("steam/ISteamGameServer");
	jmethodID callBackMethodID = env->GetStaticMethodID(iSteamGameServerClass, "OnP2PSessionRequest", "(IJ)V");
	if(callBackMethodID==nullptr){
		ostringstream errorString;
		errorString << "Could not find java method OnP2PSessionRequest(IJ)V" << std::endl;
		log(&errorString.str()[0]);
		return;
	}
	env->CallStaticVoidMethod(iSteamGameServerClass, callBackMethodID, (jint)pCallback->k_iCallback, (jlong)pCallback->m_steamIDRemote.ConvertToUint64());
}


//-----------------------------------------------------------------------------
// Purpose: Handle clients disconnecting
//-----------------------------------------------------------------------------
void SteamGameServerCallbackClass::OnP2PSessionConnectFail( P2PSessionConnectFail_t *pCallback ){
	if(pCallback==nullptr){
		log("OnP2PSessionConnectFail was passed null");
		return;
	}
//	steamjni::SteamCallbackClass::log(this->jvm,"OnP2PSessionConnectFail");
//	ostringstream nativeStringBuffer;
//	nativeStringBuffer << "P2PSessionConnectFail_t result=" << pCallback;
//	steamjni::SteamCallbackClass::log(this->jvm,&nativeStringBuffer.str()[0]);

	JNIEnv *env;
	this->jvm->AttachCurrentThread((void **) &env, nullptr);

	jclass iSteamGameServerClass = env->FindClass("steam/ISteamGameServer");
	jmethodID callBackMethodID = env->GetStaticMethodID(iSteamGameServerClass, "OnP2PSessionConnectFail", "(IIJ)V");
	if(callBackMethodID==nullptr){
		ostringstream errorString;
		errorString << "Could not find java method OnP2PSessionConnectFail(IIJ)V" << std::endl;
		log(&errorString.str()[0]);
		return;
	}
	env->CallStaticVoidMethod(iSteamGameServerClass, callBackMethodID, (jint)pCallback->k_iCallback, (jint)pCallback->m_eP2PSessionError, (jlong)pCallback->m_steamIDRemote.ConvertToUint64());
}

}
