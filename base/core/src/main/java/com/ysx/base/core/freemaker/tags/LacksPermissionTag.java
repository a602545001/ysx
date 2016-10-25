package com.ysx.base.core.freemaker.tags;

/**
 * description: 缺少权限时用的指�?
 *
 * @version 2016�?�?3�?下午10:23:38
 * @see
 * modify content------------author------------date
 */
public class LacksPermissionTag extends PermissionTag {

	@Override
	protected boolean showTagBody(String p) {
		return getSubject() != null && !getSubject().isPermitted(p);
	}

}
