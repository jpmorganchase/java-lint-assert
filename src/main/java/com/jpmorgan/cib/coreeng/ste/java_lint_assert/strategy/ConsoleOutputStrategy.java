package com.jpmorgan.cib.coreeng.ste.java_lint_assert.strategy;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.TestMethodContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ConsoleOutputStrategy {

    public final int TABLEPADDING = 4;
    public final char SEPERATOR_CHAR = '-';

    private final Set<TestMethodContext> context;
    private final Collection<String> headers;
    private ArrayList<Integer> maxLength;

    public ConsoleOutputStrategy(Collection<String> headers, Set<TestMethodContext> testMethodsContext) {
        this.headers = headers;
        this.context = testMethodsContext;
    }

    public String render(){
        StringBuilder content = this.renderHeaders();
        return content.append(this.renderBody()).toString();
    }

    StringBuilder renderHeaders() {
        StringBuilder sb = new StringBuilder();

        int rowLength = 0;
        String rowSeperator = "";

        String padder = "";
        //Create padding string containing just containing spaces
        for(int i = 0; i < TABLEPADDING; i++){
            padder += " ";
        }

        this.maxLength =  new ArrayList<>();
        for (String header: headers){
            maxLength.add(header.length());
        }

        //Create the rowSeperator
        renderSeparatorRow(sb);
        sb.append("\n");
        renderHeaderRow(sb, padder);
        sb.append("\n");
        renderSeparatorRow(sb);
        sb.append("\n");

        return sb;
    }

    private void renderHeaderRow(StringBuilder sb, String padder) {
        for(String header: headers){
            sb.append("|");
            sb.append(padder);
            sb.append(header);
            sb.append(padder);
        }
        sb.append("|");
    }

    private void renderSeparatorRow(StringBuilder sb) {
        for(int i = 0; i < maxLength.size(); i++){
            sb.append("|");
            for(int j = 0; j < maxLength.get(i)+(TABLEPADDING*2); j++){
                sb.append(SEPERATOR_CHAR);
            }
        }
        sb.append("|");
    }

    StringBuilder renderBody(){
        return new StringBuilder();
    }
}
