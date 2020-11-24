package org.lint.azzert.context;

import java.util.Set;

public class MethodCallMetadata {

    final String ownerClass;
    final String methodName;
    final int atLineNumber;

    public MethodCallMetadata(String ownerClass, String methodName, int atLineNumber) {
        this.ownerClass = ownerClass;
        this.methodName = methodName;
        this.atLineNumber = atLineNumber;
    }

    public String getOwnerClass() {
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

    @Override
    public String toString() {
        return "MethodCallMetadata{" +
                "ownerClass='" + ownerClass + '\'' +
                ", methodName='" + methodName + '\'' +
                ", atLineNumber=" + atLineNumber +
                '}';
    }

    public boolean isInOneOfExtLibs(Set<String> extensionLibPackages) {
        boolean isIn = false;
        for (String lib: extensionLibPackages) {
            if ( lib.contains("#")) {
                isIn = lib.equals(this.getOwnerClass() + "#" + this.getMethodName());
            }  else {
                isIn = lib.contains(this.getOwnerClass());
            }
            if (isIn) break;
        }
        return isIn;
    }
}
