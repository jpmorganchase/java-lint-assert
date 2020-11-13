package org.lint.azzert.strategy.output;

import org.lint.azzert.context.MethodMetadata;

import java.util.*;
import java.util.function.BiConsumer;

public class ToStringStrategy {

    public static final String PIPE = "|";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final int PADDING = 4;
    public static final char SEPARATOR_CHAR = '-';

    public Collection<String> getTableHeaders (){
            return Collections.unmodifiableList(Arrays.asList("Package", "Test file name", "Test method name", "Validations count"));
    }

    private ArrayList<Integer> maxLength;
    private final Set<MethodMetadata> testMethods;

    public ToStringStrategy(Set<MethodMetadata> methodMetadata) {
        this.testMethods = new HashSet<>(methodMetadata);
        this.maxLength = new ArrayList<>();
    }

    public String render() {
        calculateEachCellWidth();
        StringBuilder content = this.renderHeader();
        return content.append(this.renderBody()).toString();
    }

    protected void calculateEachCellWidth() {
        //the initial cell width must fit the header's label, so that a 'no tests' table is formatted
        for (String header : getTableHeaders()) {
            maxLength.add(header.length());
        }

        //set the initial cell width to be equal to its longest content
        for (MethodMetadata context : testMethods) {
            final BiConsumer<Integer, Integer> consumer = (index, size) -> {
                if (maxLength.get(index) < size) {
                    maxLength.set(index, size);
                }
            };
            consumer.accept(0, context.getPackageName().length());
            consumer.accept(1, context.getFileName().length());
            consumer.accept(2, context.getMethodName().length());
            consumer.accept(3, context.getMethodCalls().size());
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
        for (MethodMetadata method : testMethods) {
            renderRow(method, sb);
            sb.append(PIPE);
            sb.append(LINE_SEPARATOR);
        }
        return sb;
    }

    protected void renderHeaderRow(StringBuilder sb) {
        int i = 0;
        for (String header : getTableHeaders()) {
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

    protected void renderRow(MethodMetadata method, StringBuilder sb) {
        renderCell(sb, method.getPackageName(), 0);
        renderCell(sb, method.getFileName(), 1);
        renderCell(sb, method.getMethodName(), 2);

        renderCell(sb, method.getVerificationsCount(), 3);
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
