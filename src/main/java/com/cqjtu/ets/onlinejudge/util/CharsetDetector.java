package com.cqjtu.ets.onlinejudge.util;

import java.io.IOException;
import java.io.InputStream;

public interface CharsetDetector {
	public boolean detect(InputStream in) throws IOException ;
	
	public String getCharsetName();
}
