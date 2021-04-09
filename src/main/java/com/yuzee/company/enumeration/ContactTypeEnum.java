package com.yuzee.company.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContactTypeEnum {
	EMAIL("Email","email.svg",false),
	PHONE("Phone","phone.svg",false),
	SKYPE("Skype","skype.svg",false),
	WEBSITE("Website","web_icon.svg",false),
	ENQUIRY("Enquiry","enquiry.svg",false),
	WHATSAPP("WhatsApp","whatsApp.svg",false),
	GOOGLE_HANGOUT("Google Hangouts","hangout.svg",false),
	IMO("IMO","imo.svg",false),
	KAKAO_TALK("Kakao Talk","kakao.svg",false),
	KIK_MESSENGER("Kik Messenger","kikk.svg",false),
	LINE("Line","line.svg",false),
	LINKED_IN("Linked In","linkedIn.svg",false),
	FB_MESSANGER("Messanger","messanger.svg",false),
	TANGO("Tango","tango.svg",false),
	TELEGRAM("Telegram","telegram.svg",false),
	TIK_TOK("Tik Tok","tikTok.svg",false),
	VIBER("Viber","viber.svg",false),
	WE_CHAT("WeChat","weChat.svg",false),
	YABB_MESSENGER("Yabb Messenger","yab.svg",false),
	OTHER("Other","other.svg",false);
	
	@Getter private String displayName;
	@Getter @Setter private String imageName;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Getter @Setter private boolean initialized;
	
	
	public String getName() {
		return this.name();
	}
	
}
