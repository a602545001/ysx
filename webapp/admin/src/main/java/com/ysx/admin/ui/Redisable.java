package com.ysx.admin.ui;

import java.io.Serializable;

public interface Redisable<K> extends Serializable{
	
	public K getKey();

}
