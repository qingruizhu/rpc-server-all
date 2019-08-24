package com.ddup.rpc;
import java.io.Serializable;
/**
 * 请求体
 */
public class RpcRequest implements Serializable {
    public String className;//类名
    public String methodName;//方法名
    public Object[] parameters;//请求实参
    public String version;//版本号

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public Object[] getParameters() {
        return parameters;
    }
    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
