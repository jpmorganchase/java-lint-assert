package com.jpmorgan.cib.coreeng.ste.java_lint_assert.strategy;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.TestMethodContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class ConsoleOutputStrategy {

    public static final String PIPE = "|";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public final int PADDING = 4;
    public final char SEPARATOR_CHAR = '-';

    private final Set<TestMethodContext> contexts;
    private final Collection<String> headers;
    private ArrayList<Integer> maxLength;

    public ConsoleOutputStrategy(Set<TestMethodContext> testMethodContexts){
        this( Arrays.asList("Package",
                                "Test file name",
                                "Test method name",
                                "# asserts"), testMethodContexts);
    }

    public ConsoleOutputStrategy(Collection<String> headers, Set<TestMethodContext> testMethodsContext) {
        this.headers = headers;
        this.contexts = testMethodsContext;
    }

    public String render() {
        StringBuilder content = this.renderHeaders();
        return content.append(this.renderBody()).toString();
    }

    StringBuilder renderHeaders() {
        StringBuilder sb = new StringBuilder();

        this.maxLength = new ArrayList<>();
        for (String header : headers) {
            maxLength.add(header.length());
        }

//        int i = 0;
        for (TestMethodContext context : contexts) {
            if (maxLength.get(0) < context.getPackageName().length())
                maxLength.set(0, context.getPackageName().length());
            if (maxLength.get(1) < context.getFileName().length())
                maxLength.set(1, context.getPackageName().length());
            if (maxLength.get(2) < context.getMethodName().length())
                maxLength.set(2, context.getMethodName().length());
            if (maxLength.get(3) < context.getAssertMethodsAtLineNumbers().size())
                maxLength.set(3, context.getAssertMethodsAtLineNumbers().size());
//            i %= headers.size();
        }


        renderSeparatorRow(sb);
        sb.append(LINE_SEPARATOR);
        renderHeaderRow(sb);
        sb.append(LINE_SEPARATOR);
        renderSeparatorRow(sb);
        sb.append(LINE_SEPARATOR);

        for (TestMethodContext context : contexts) {
            renderRow(context, sb);
            sb.append(PIPE);
            sb.append(LINE_SEPARATOR);
        }

        return sb;
    }

    void renderRow(TestMethodContext context, StringBuilder sb) {
        renderCell(sb, context.getPackageName(), 0);
        renderCell(sb, context.getFileName(), 1);
        renderCell(sb, context.getMethodName(), 2);
        renderCell(sb, context.getAssertMethodsAtLineNumbers().size(), 3);
    }

    private void renderCell(StringBuilder sb, Object text, int cell) {
        sb.append(PIPE);
        sb.append(pad(maxLength.get(cell), text.toString(), false));
        sb.append(text);
        sb.append(pad(maxLength.get(cell), text.toString(), true));
    }

    String pad(int cellWidth, String cellValue, boolean isRightHandSide) {
        StringBuilder padder = new StringBuilder();

        int emptySpaces = cellWidth - cellValue.length();
        if (isRightHandSide && (emptySpaces % 2 == 1))
            emptySpaces++;
        emptySpaces /= 2;

        for (int i = 0; i < emptySpaces + PADDING; i++) {
            padder.append(' ');
        }
        return padder.toString();
    }

    void renderHeaderRow(StringBuilder sb) {
        int i = 0;
        for (String header : headers) {
            renderCell(sb, header, i);
            i++;
        }
        sb.append(PIPE);
    }

    void renderSeparatorRow(StringBuilder sb) {
        for (Integer cellLengh : maxLength) {
            sb.append(PIPE);
            for (int j = 0; j < cellLengh + (PADDING * 2); j++) {
                sb.append(SEPARATOR_CHAR);
            }
        }
        sb.append(PIPE);
    }

    StringBuilder renderBody() {
        return new StringBuilder();
    }
}
