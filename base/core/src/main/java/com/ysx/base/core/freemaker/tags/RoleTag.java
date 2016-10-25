package com.ysx.base.core.freemaker.tags;


import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.util.Map;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.RoleTag}</p>
 */
public abstract class RoleTag extends SecureTag {
    @SuppressWarnings("rawtypes")
	String getName(Map params) {
        return getParam(params, "name");
    }
    
    @SuppressWarnings("rawtypes")
	@Override
    protected void verifyParameters(Map params) throws TemplateModelException {
        String name = getName(params);

        if (name == null || name.length() == 0) {
            throw new TemplateModelException("The 'name' tag attribute must be set.");
        }
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String p = getName(params);

        boolean show = showTag(p);
        if (show) {
            renderBody(env, body);
        }
    }

    protected boolean isRole(String p) {
        return getSubject() != null && getSubject().hasRole(p);
    }

    protected abstract boolean showTag(String p);
}

