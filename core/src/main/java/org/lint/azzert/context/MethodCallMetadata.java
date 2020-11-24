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

    public String getFullyQualifiedPackageName() {
        String ownerPackage = replaceSlashesWithDots();
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
                String fullyQualifiedClassName = replaceSlashesWithDots() + "#" + this.getMethodName();
                isIn = lib.equals(fullyQualifiedClassName);
            }  else {
                isIn = lib.contains(getFullyQualifiedPackageName());
            }
            if (isIn) break;
        }
        return isIn;
    }

     String replaceSlashesWithDots() {
        return ownerClass.replace('/', '.');// example: 'org/junit/Assert'
    }
}
