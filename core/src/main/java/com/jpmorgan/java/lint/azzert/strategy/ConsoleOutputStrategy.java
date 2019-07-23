package com.jpmorgan.java.lint.azzert.strategy;

import com.jpmorgan.java.lint.azzert.context.TestMethodContext;

import java.util.*;
import java.util.function.BiConsumer;

public class ConsoleOutputStrategy {

    public static final String PIPE = "|";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final int PADDING = 4;
    public static final char SEPARATOR_CHAR = '-';

    public static final Collection<String> HEADERS = Collections.unmodifiableList(Arrays.asList("Package", "Test file name", "Test method name", "# asserts"));

    private ArrayList<Integer> maxLength;
    private final Set<TestMethodContext> contexts;

    public ConsoleOutputStrategy(Set<TestMethodContext> testMethodContexts) {
        this.contexts = new HashSet<>(testMethodContexts);
        this.maxLength = new ArrayList<>();
    }

    public String render() {
        calculateEachCellWidth();

        StringBuilder content = this.renderHeader();
        return content.append(this.renderBody()).toString();
    }

    protected void calculateEachCellWidth() {
        //the initial cell width must fit the header's label, so that a 'no tests' table is formatted
        for (String header : HEADERS) {
            maxLength.add(header.length());
        }

        for (TestMethodContext context : contexts) {
            final BiConsumer<Integer, Integer> consumer = (index, size) -> {
                if (maxLength.get(index) < size) {
                    maxLength.set(index, size);
                }
            };
            consumer.accept(0, context.getPackageName().length());
            consumer.accept(1, context.getFileName().length());
            consumer.accept(2, context.getMethodName().length());
            consumer.accept(3, context.getAssertMethodsAtLineNumbers().size());
        }
    }

    protected StringBuilder renderHeader() {
        final StringBuilder sb = new StringBuilder();

        renderSeparatorRow(sb);
        sb.append(LINE_SEPARATOR);
        renderHeaderRow(sb);
        sb.append(LINE_SEPARATOR);
        renderSeparatorRow(sb);
        sb.append(LINE_SEPARATOR);

        return sb;
    }

    protected StringBuilder renderBody() {

        final StringBuilder sb = new StringBuilder();

        for (TestMethodContext context : contexts) {
            renderRow(context, sb);
            sb.append(PIPE);
            sb.append(LINE_SEPARATOR);
        }
        return sb;
    }

    protected void renderHeaderRow(StringBuilder sb) {
        int i = 0;
        for (String header : HEADERS) {
            renderCell(sb, header, i);
            i++;
        }
        sb.append(PIPE);
    }

    protected void renderSeparatorRow(StringBuilder sb) {
        for (Integer cellLengh : maxLength) {
            sb.append(PIPE);
            for (int j = 0; j < cellLengh + (PADDING * 2); j++) {
                sb.append(SEPARATOR_CHAR);
            }
        }
        sb.append(PIPE);
    }

    protected void renderRow(TestMethodContext context, StringBuilder sb) {
        renderCell(sb, context.getPackageName(), 0);
        renderCell(sb, context.getFileName(), 1);
        renderCell(sb, context.getMethodName(), 2);
        renderCell(sb, context.getAssertMethodsAtLineNumbers().size(), 3);
    }

    protected void renderCell(StringBuilder sb, Object text, int cell) {
        sb.append(PIPE);
        sb.append(pad(maxLength.get(cell), text.toString(), false));
        sb.append(text);
        sb.append(pad(maxLength.get(cell), text.toString(), true));
    }

    protected String pad(int cellWidth, String cellValue, boolean isRightHandSide) {
        StringBuilder padder = new StringBuilder();

        int emptySpaces = cellWidth - cellValue.length();
        if (isRightHandSide && (emptySpaces % 2 == 1)) {
            emptySpaces++;
        }
        emptySpaces /= 2;

        for (int i = 0; i < emptySpaces + PADDING; i++) {
            padder.append(' ');
        }
        return padder.toString();
    }

}
