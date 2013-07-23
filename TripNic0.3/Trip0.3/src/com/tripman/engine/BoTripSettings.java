package com.tripman.engine;

public class BoTripSettings {

	private boolean iEnableSms;
	private boolean iEnableEmail;
	private boolean iSms_sync;
	private boolean iEmail_sync;
	
	BoTripSettings()
	{
		iEnableSms = false;
		iEnableEmail = false;
		iSms_sync = false;
		iEmail_sync = false;
	}
	
	public boolean isEnableSms() {
		return iEnableSms;
	}
	
	public void setEnableSms(boolean aEnableSms) {
		iEnableSms = aEnableSms;
	}

	public boolean isEnableEmail() {
		return iEnableEmail;
	}

	public void setEnableEmail(boolean aEnableEmail) {
		iEnableEmail = aEnableEmail;
	}

	public boolean isSms_sync() {
		return iSms_sync;
	}

	public void setSms_sync(boolean aSms_sync) {
		iSms_sync = aSms_sync;
	}

	public boolean isEmail_sync() {
		return iEmail_sync;
	}

	public void setEmail_sync(boolean aEmail_sync) {
		iEmail_sync = aEmail_sync;
	}
}
