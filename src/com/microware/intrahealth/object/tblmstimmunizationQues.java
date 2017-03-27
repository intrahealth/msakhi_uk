package com.microware.intrahealth.object;

public class tblmstimmunizationQues {
	private int Q_id;
	private int Grp;
	private int Q_type;
	private String Ftext;
	private int Y_qid;
	private int N_qid;
	private String Oinfo;
	private String def;
	private String help;
	private String pans;
	private String qvid;
	private String dispseq;
	private String oprompt;
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
	public String getFtext() {
		return Ftext;
	}
	public void setFtext(String ftext) {
		Ftext = ftext;
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
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	public String getPans() {
		return pans;
	}
	public void setPans(String pans) {
		this.pans = pans;
	}
	public String getQvid() {
		return qvid;
	}
	public void setQvid(String qvid) {
		this.qvid = qvid;
	}
	public String getDispseq() {
		return dispseq;
	}
	public void setDispseq(String dispseq) {
		this.dispseq = dispseq;
	}
	public String getOprompt() {
		return oprompt;
	}
	public void setOprompt(String oprompt) {
		this.oprompt = oprompt;
	}
	
}
