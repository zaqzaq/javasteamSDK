# Code style
There aren't any hard rules for coding style, please use the following JetBrains IDE styles or as close as possible.

## JetBrains IntelliJ (Java and Kotlin)
```xml
<code_scheme name="NimblyGames" version="173">
  <option name="OTHER_INDENT_OPTIONS">
    <value>
      <option name="USE_TAB_CHARACTER" value="true" />
      <option name="SMART_TABS" value="true" />
    </value>
  </option>
  <option name="LINE_SEPARATOR" value="&#xA;" />
  <option name="INSERT_INNER_CLASS_IMPORTS" value="true" />
  <option name="CLASS_COUNT_TO_USE_IMPORT_ON_DEMAND" value="10" />
  <option name="RIGHT_MARGIN" value="160" />
  <option name="FORMATTER_TAGS_ENABLED" value="true" />
  <option name="SOFT_MARGINS" value="160" />
  <JavaCodeStyleSettings>
    <option name="ANNOTATION_PARAMETER_WRAP" value="5" />
    <option name="CLASS_NAMES_IN_JAVADOC" value="3" />
    <option name="INSERT_INNER_CLASS_IMPORTS" value="true" />
    <option name="CLASS_COUNT_TO_USE_IMPORT_ON_DEMAND" value="20" />
    <option name="NAMES_COUNT_TO_USE_IMPORT_ON_DEMAND" value="20" />
    <option name="PACKAGES_TO_USE_IMPORT_ON_DEMAND">
      <value />
    </option>
    <option name="JD_ALIGN_PARAM_COMMENTS" value="false" />
    <option name="JD_ALIGN_EXCEPTION_COMMENTS" value="false" />
    <option name="JD_ADD_BLANK_AFTER_PARM_COMMENTS" value="true" />
    <option name="JD_ADD_BLANK_AFTER_RETURN" value="true" />
    <option name="JD_KEEP_INVALID_TAGS" value="false" />
    <option name="JD_INDENT_ON_CONTINUATION" value="true" />
  </JavaCodeStyleSettings>
  <codeStyleSettings language="JAVA">
    <option name="RIGHT_MARGIN" value="160" />
    <option name="KEEP_LINE_BREAKS" value="false" />
    <option name="KEEP_FIRST_COLUMN_COMMENT" value="false" />
    <option name="ALIGN_MULTILINE_PARAMETERS" value="false" />
    <option name="ALIGN_MULTILINE_ASSIGNMENT" value="true" />
    <option name="ALIGN_MULTILINE_PARENTHESIZED_EXPRESSION" value="true" />
    <option name="CALL_PARAMETERS_WRAP" value="5" />
    <option name="METHOD_PARAMETERS_WRAP" value="5" />
    <option name="RESOURCE_LIST_WRAP" value="5" />
    <option name="EXTENDS_LIST_WRAP" value="5" />
    <option name="THROWS_LIST_WRAP" value="5" />
    <option name="THROWS_KEYWORD_WRAP" value="1" />
    <option name="METHOD_CALL_CHAIN_WRAP" value="5" />
    <option name="BINARY_OPERATION_WRAP" value="5" />
    <option name="TERNARY_OPERATION_WRAP" value="5" />
    <option name="KEEP_SIMPLE_BLOCKS_IN_ONE_LINE" value="true" />
    <option name="KEEP_SIMPLE_METHODS_IN_ONE_LINE" value="true" />
    <option name="KEEP_SIMPLE_LAMBDAS_IN_ONE_LINE" value="true" />
    <option name="KEEP_SIMPLE_CLASSES_IN_ONE_LINE" value="true" />
    <option name="FOR_STATEMENT_WRAP" value="5" />
    <option name="ARRAY_INITIALIZER_WRAP" value="5" />
    <option name="ASSIGNMENT_WRAP" value="1" />
    <option name="WRAP_COMMENTS" value="true" />
    <option name="ASSERT_STATEMENT_WRAP" value="5" />
    <option name="WRAP_LONG_LINES" value="true" />
    <option name="METHOD_ANNOTATION_WRAP" value="5" />
    <option name="CLASS_ANNOTATION_WRAP" value="5" />
    <option name="FIELD_ANNOTATION_WRAP" value="5" />
    <option name="PARAMETER_ANNOTATION_WRAP" value="5" />
    <option name="VARIABLE_ANNOTATION_WRAP" value="5" />
    <option name="ENUM_CONSTANTS_WRAP" value="2" />
    <indentOptions>
      <option name="INDENT_SIZE" value="3" />
      <option name="CONTINUATION_INDENT_SIZE" value="6" />
      <option name="TAB_SIZE" value="3" />
      <option name="SMART_TABS" value="true" />
    </indentOptions>
  </codeStyleSettings>
  <codeStyleSettings language="kotlin">
    <option name="KEEP_LINE_BREAKS" value="false" />
    <option name="KEEP_FIRST_COLUMN_COMMENT" value="false" />
    <option name="ALIGN_MULTILINE_PARAMETERS" value="false" />
    <option name="ALIGN_MULTILINE_BINARY_OPERATION" value="true" />
    <option name="CALL_PARAMETERS_WRAP" value="5" />
    <option name="METHOD_PARAMETERS_WRAP" value="5" />
    <option name="METHOD_PARAMETERS_LPAREN_ON_NEXT_LINE" value="true" />
    <option name="METHOD_PARAMETERS_RPAREN_ON_NEXT_LINE" value="true" />
    <option name="EXTENDS_LIST_WRAP" value="5" />
    <option name="METHOD_CALL_CHAIN_WRAP" value="5" />
    <option name="ASSIGNMENT_WRAP" value="5" />
    <option name="METHOD_ANNOTATION_WRAP" value="5" />
    <option name="CLASS_ANNOTATION_WRAP" value="5" />
    <option name="FIELD_ANNOTATION_WRAP" value="5" />
    <option name="PARAMETER_ANNOTATION_WRAP" value="5" />
    <option name="VARIABLE_ANNOTATION_WRAP" value="5" />
    <option name="ENUM_CONSTANTS_WRAP" value="5" />
    <indentOptions>
      <option name="INDENT_SIZE" value="3" />
      <option name="CONTINUATION_INDENT_SIZE" value="6" />
      <option name="TAB_SIZE" value="3" />
      <option name="SMART_TABS" value="true" />
    </indentOptions>
  </codeStyleSettings>
</code_scheme>
```

## JetBrains CLion (C++)
```xml
<code_scheme name="NimblyGames" version="173">
  <option name="RIGHT_MARGIN" value="160" />
  <option name="FORMATTER_TAGS_ENABLED" value="true" />
  <option name="SOFT_MARGINS" value="160" />
  <Objective-C>
    <option name="INDENT_NAMESPACE_MEMBERS" value="3" />
    <option name="INDENT_C_STRUCT_MEMBERS" value="3" />
    <option name="INDENT_CLASS_MEMBERS" value="3" />
    <option name="INDENT_INSIDE_CODE_BLOCK" value="3" />
    <option name="KEEP_CASE_EXPRESSIONS_IN_ONE_LINE" value="true" />
    <option name="KEEP_DIRECTIVE_AT_FIRST_COLUMN" value="false" />
    <option name="FUNCTION_PARAMETERS_WRAP" value="5" />
    <option name="FUNCTION_PARAMETERS_NEW_LINE_AFTER_LPAR" value="true" />
    <option name="LAMBDA_CAPTURE_LIST_WRAP" value="5" />
    <option name="FUNCTION_CALL_ARGUMENTS_WRAP" value="5" />
    <option name="FUNCTION_CALL_ARGUMENTS_ALIGN_MULTILINE" value="false" />
    <option name="SHIFT_OPERATION_WRAP" value="5" />
    <option name="TEMPLATE_DECLARATION_STRUCT_WRAP" value="5" />
    <option name="TEMPLATE_DECLARATION_FUNCTION_WRAP" value="5" />
    <option name="TEMPLATE_PARAMETERS_WRAP" value="5" />
    <option name="TEMPLATE_CALL_ARGUMENTS_WRAP" value="5" />
    <option name="CLASS_CONSTRUCTOR_INIT_LIST_WRAP" value="5" />
    <option name="ALIGN_INIT_LIST_IN_COLUMNS" value="false" />
  </Objective-C>
  <Objective-C-extensions>
    <extensions>
      <pair source="cpp" header="h" fileNamingConvention="NONE" />
      <pair source="c" header="h" fileNamingConvention="NONE" />
    </extensions>
  </Objective-C-extensions>
  <codeStyleSettings language="ObjectiveC">
    <option name="RIGHT_MARGIN" value="160" />
    <option name="KEEP_LINE_BREAKS" value="false" />
    <option name="KEEP_FIRST_COLUMN_COMMENT" value="false" />
    <option name="KEEP_CONTROL_STATEMENT_IN_ONE_LINE" value="false" />
    <option name="ALIGN_MULTILINE_CHAINED_METHODS" value="true" />
    <option name="ALIGN_MULTILINE_ASSIGNMENT" value="false" />
    <option name="METHOD_CALL_CHAIN_WRAP" value="5" />
    <option name="TERNARY_OPERATION_WRAP" value="5" />
    <option name="FOR_STATEMENT_WRAP" value="5" />
    <option name="ARRAY_INITIALIZER_WRAP" value="5" />
    <option name="IF_BRACE_FORCE" value="3" />
    <option name="DOWHILE_BRACE_FORCE" value="3" />
    <option name="WHILE_BRACE_FORCE" value="3" />
    <option name="FOR_BRACE_FORCE" value="3" />
    <indentOptions>
      <option name="INDENT_SIZE" value="3" />
      <option name="CONTINUATION_INDENT_SIZE" value="6" />
      <option name="TAB_SIZE" value="3" />
    </indentOptions>
  </codeStyleSettings>
</code_scheme>
```

## Automated formatting
A git hook, Gradle plugin, or equivalent for auto formatting would be a welcome addition. 