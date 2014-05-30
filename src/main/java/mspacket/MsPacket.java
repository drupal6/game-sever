package mspacket;

import until.ParamObject;
import until.ObjectUtil;


public class MsPacket {

	private int actId;
	private int modId;
	private int checkSum;
	private int length;
	private byte[] buff;
	private int position = 0;
	
	public short readShort() {
		byte b1 = readByte();
		byte b2 = readByte();
		return (short)((b1 << 4 | b2) & 0xFF);
	}
	
	public void writeShort(short s) {
		byte b1 = (byte) (s >> 4 & 0xFF);
		byte b2 = (byte) (s & 0xFF);
		if(position > 1) {
			position --;
			buff[position] = b2;
			position --;
			buff[position] = b1;
		}else {
			length = getLength() + 2;
			byte[] newBuff = new byte[length];
			newBuff[0] = b1;
			newBuff[1] = b2;
			System.arraycopy(buff, position, newBuff, 2, length);
			position = 0;
		}
	}
	
	public int readInt() {
		byte b1 = readByte();
		byte b2 = readByte();
		byte b3 = readByte();
		byte b4 = readByte();
		return (int)((b1 << 12 | b2 << 8 | b3 << 4 | b4) & 0xFFFF);
	}
	
	public void writeInt(int i) {
		byte i1 = (byte) (i >> 12 & 0xFF);
		byte i2 = (byte) (i >> 8 & 0xFF);
		byte i3 = (byte) (i >> 4 & 0xFF);
		byte i4 = (byte) (i & 0xFF);
		if(position > 3) {
			position --;
			buff[position] = i4;
			position --;
			buff[position] = i3;
			position --;
			buff[position] = i2;
			position --;
			buff[position] = i1;
		}else {
			length = getLength() + 4;
			byte[] newBuff = new byte[length];
			newBuff[0] = i4;
			newBuff[1] = i3;
			newBuff[2] = i2;
			newBuff[3] = i1;
			System.arraycopy(buff, position, newBuff, 4, length);
			position = 0;
		}
	}
	
	public ParamObject readObject() {
		if(isAvalible() <= 0) {
			return null;
		}
		return ObjectUtil.toObject(getBuff());
	}
	
	private byte readByte() {
		if(position >= length -1) {
			return 0;
		}
		byte b = buff[position];
		position ++;
		return b;
	}
	
	public int getLength() {
		return length - position;
	}
	public int getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}
	public byte[] getBuff() {
		byte[] temBuff = new byte[getLength()];
		System.arraycopy(buff, position, temBuff, 0, getLength());
		return temBuff;
	}
	public byte[] readBuff() {
		byte[] temBuff = new byte[getLength()];
		System.arraycopy(buff, position, temBuff, 0, getLength());
		position = length;
		return temBuff;
	}
	
	public void setBuff(byte[] buff) {
		this.buff = buff;
		this.length = buff.length;
	}
	public int isAvalible() {
		return length - position;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	public int getModId() {
		return modId;
	}

	public void setModId(int modId) {
		this.modId = modId;
	}
}
