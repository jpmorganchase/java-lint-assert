package org.lint.azzert.context;

public class MethodCallMetadata {

    final String ownerClass;
    final String methodName;
    final int atLineNumber;

    public MethodCallMetadata(String ownerClass, String methodName, int atLineNumber) {
        this.ownerClass = ownerClass;
        this.methodName = methodName;
        this.atLineNumber = atLineNumber;
    }

    public String getOwnnerClass() {
        return ownerClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getAtLineNumber() {
        return atLineNumber;
    }

    public String getOwnerPackage() {
        String ownerPackage = ownerClass.replace('/', '.'); // example: 'org/junit/Assert'
        int i = ownerPackage.lastIndexOf('.');
        ownerPackage = i > 0 ? ownerPackage.substring(0, i) : null;

        return ownerPackage;
    }
}
