/**
 * This software is released as part of the Pumpernickel project.
 * 
 * All com.pump resources in the Pumpernickel project are distributed under the
 * MIT License:
 * https://raw.githubusercontent.com/mickleness/pumpernickel/master/License.txt
 * 
 * More information about the Pumpernickel project is available here:
 * https://mickleness.github.io/pumpernickel/
 */
package com.pump.animation.quicktime;

import java.io.IOException;
import java.io.InputStream;

import com.pump.io.GuardedOutputStream;

/** This is not a public class because I expect to make some significant
 * changes to this project in the next year.
 * <P>Use at your own risk.  This class (and its package) may change in future releases.
 * <P>Not that I'm promising there will be future releases.  There may not be.  :)
 */
public class ChunkOffsetAtom extends LeafAtom {
	int version = 0;
	int flags = 0;
	long[] offsetTable = new long[0];
	
	public ChunkOffsetAtom(int version, int flags) {
		super(null);
		this.version = version;
		this.flags = flags;
	}
	
	public ChunkOffsetAtom() {
		super(null);
	}
	
	public long getChunkOffset(int index) {
		return offsetTable[index];
	}
	
	public int getChunkOffsetCount() {
		return offsetTable.length;
	}
	
	public void setChunkOffset(int index,long value) {
		offsetTable[index] = value;
	}
	
	public ChunkOffsetAtom(Atom parent,InputStream in) throws IOException {
		super(parent);
		version = in.read();
		flags = read24Int(in);
		int arraySize = (int)read32Int(in);
		offsetTable = new long[arraySize];
		for(int a = 0; a<offsetTable.length; a++) {
			offsetTable[a] = read32Int(in);
		}
	}
	
	public void addChunkOffset(long offset) {
		long[] newArray = new long[offsetTable.length+1];
		System.arraycopy(offsetTable,0,newArray,0,offsetTable.length);
		newArray[newArray.length-1] = offset;
		offsetTable = newArray;
	}
	
	@Override
	protected String getIdentifier() {
		return "stco";
	}


	@Override
	protected long getSize() {
		return 16+offsetTable.length*4;
	}


	@Override
	protected void writeContents(GuardedOutputStream out) throws IOException {
		out.write(version);
		write24Int(out,flags);
		write32Int(out,offsetTable.length);
		for(int a = 0; a<offsetTable.length; a++) {
			write32Int(out,offsetTable[a]);
		}
	}


	@Override
	public String toString() {
		String entriesString;
		if(offsetTable.length>50 && ABBREVIATE) {
			entriesString = "[ ... ]";
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("[ ");
			for(int a = 0; a<offsetTable.length; a++) {
				if(a!=0) {
					sb.append(", ");
				}
				sb.append(offsetTable[a]);
			}
			sb.append(" ]");
			entriesString = sb.toString();
		}
		
		return "ChunkOffsetAtom[ version="+version+", "+
		"flags="+flags+", "+
		"sizeTable="+entriesString+"]";
	}
}