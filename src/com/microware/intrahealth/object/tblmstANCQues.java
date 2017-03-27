package com.microware.intrahealth.object;

public class tblmstANCQues {
	private int Q_id;
	private int Grp;
	private int Q_type;
	private String Qtext;
	private String Ftext;
	private int HV1;
	private int HV2;
	private int HV3;
	private int HV4;
	private int Y_qid;
	private int N_qid;
	private String Oinfo;
	private int def;
	private int help;
	private int pans;
	private int qvid;
	private int dispseq;
	private String oprompt;
	private String Audio_filename;
	private String AnsField;
	
	public String getAnsField() {
		return AnsField;
	}
	public void setAnsField(String ansField) {
		AnsField = ansField;
	}
	public int getQ_id() {
		return Q_id;
	}
	public void setQ_id(int q_id) {
		Q_id = q_id;
	}
	public int getGrp() {
		return Grp;
	}
	public void setGrp(int grp) {
		Grp = grp;
	}
	public int getQ_type() {
		return Q_type;
	}
	public void setQ_type(int q_type) {
		Q_type = q_type;
	}
	public String getQtext() {
		return Qtext;
	}
	public void setQtext(String qtext) {
		Qtext = qtext;
	}
	public String getFtext() {
		return Ftext;
	}
	public void setFtext(String ftext) {
		Ftext = ftext;
	}
	public int getHV1() {
		return HV1;
	}
	public void setHV1(int hV1) {
		HV1 = hV1;
	}
	public int getHV2() {
		return HV2;
	}
	public void setHV2(int hV2) {
		HV2 = hV2;
	}
	public int getHV3() {
		return HV3;
	}
	public void setHV3(int hV3) {
		HV3 = hV3;
	}
	public int getHV4() {
		return HV4;
	}
	public void setHV4(int hV4) {
		HV4 = hV4;
	}
	public int getY_qid() {
		return Y_qid;
	}
	public void setY_qid(int y_qid) {
		Y_qid = y_qid;
	}
	public int getN_qid() {
		return N_qid;
	}
	public void setN_qid(int n_qid) {
		N_qid = n_qid;
	}
	
	public String getOinfo() {
		return Oinfo;
	}
	public void setOinfo(String oinfo) {
		Oinfo = oinfo;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getHelp() {
		return help;
	}
	public void setHelp(int help) {
		this.help = help;
	}
	public int getPans() {
		return pans;
	}
	public void setPans(int pans) {
		this.pans = pans;
	}
	public int getQvid() {
		return qvid;
	}
	public void setQvid(int qvid) {
		this.qvid = qvid;
	}
	public int getDispseq() {
		return dispseq;
	}
	public void setDispseq(int dispseq) {
		this.dispseq = dispseq;
	}
	public String getOprompt() {
		return oprompt;
	}
	public void setOprompt(String oprompt) {
		this.oprompt = oprompt;
	}
	public String getAudio_filename() {
		return Audio_filename;
	}
	public void setAudio_filename(String audio_filename) {
		Audio_filename = audio_filename;
	}
	
}
