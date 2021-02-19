ruleset {

    description 'CodeNarc Project RuleSet'

    ruleset('rulesets/basic.xml'){
        exclude 'EmptyIfStatement'
    }
    ruleset('rulesets/braces.xml')

    ruleset('rulesets/exceptions.xml')

    ruleset('rulesets/formatting.xml'){
        exclude 'LineLength'
    }

    ruleset('rulesets/generic.xml')
    ruleset('rulesets/imports.xml')
    ruleset('rulesets/unused.xml')

    ///////////////////////////////////
    //Set of "rulesets/convention.xml" not at all included

    ConfusingTernary
    HashtableIsObsolete
    IfStatementCouldBeTernary
    InvertedIfElse
    LongLiteralWithLowerCaseL
    ParameterReassignment
    TernaryCouldBeElvis


    ///////////////////////////////////
    //Set of "rulesets/dry.xml" not at all included

    DuplicateListLiteral
    DuplicateMapLiteral


    ///////////////////////////////////
    // Set of "rulesets/naming.xml" not at all included

    AbstractClassName
    InterfaceName
    ObjectOverrideMisspelledMethodName
    ParameterName
    PropertyName
    ClassNameSameAsFilename
    ConfusingMethodName // FactoryMethodName

    ClassName {
        regex = '^[A-Z][\\$a-zA-Z0-9]*$'
    }

    FieldName {
        regex = '^_?[a-z][a-zA-Z0-9]*$'
        finalRegex = '^_?[a-z][a-zA-Z0-9]*$'
        staticFinalRegex = '^logger$|^[A-Z][A-Z_0-9]*$|^serialVersionUID$'
    }

    MethodName {
        regex = '^[a-z][\\$_a-zA-Z0-9]*$|^.*\\s.*$'
    }

    VariableName {
        finalRegex = '^[a-z][a-zA-Z0-9]*$'
    }

    ///////////////////////////////////
    // Set of "rulesets/unnecessary.xmll" not at all included

    AddEmptyString
    ConsecutiveLiteralAppends
    ConsecutiveStringConcatenation
    UnnecessaryBigDecimalInstantiation
    UnnecessaryBigIntegerInstantiation
    UnnecessaryBooleanExpression
    UnnecessaryBooleanInstantiation
    UnnecessaryCallForLastElement
    UnnecessaryCallToSubstring
    UnnecessaryCast
    UnnecessaryCatchBlock
    UnnecessaryCollectCall
    UnnecessaryCollectionCall
    UnnecessaryConstructor
    UnnecessaryDefInFieldDeclaration
    UnnecessaryDefInMethodDeclaration
    UnnecessaryDefInVariableDeclaration
    UnnecessaryDotClass
    UnnecessaryDoubleInstantiation
    UnnecessaryElseStatement
    UnnecessaryFinalOnPrivateMethod
    UnnecessaryFloatInstantiation
    UnnecessaryIfStatement
    UnnecessaryInstanceOfCheck
    UnnecessaryInstantiationToGetClass
    UnnecessaryIntegerInstantiation
    UnnecessaryLongInstantiation
    UnnecessaryModOne
    UnnecessaryNullCheck
    UnnecessaryNullCheckBeforeInstanceOf
    UnnecessaryObjectReferences
    UnnecessaryOverridingMethod
    UnnecessaryPackageReference
    UnnecessaryParenthesesForMethodCallWithClosure
    UnnecessaryPublicModifier
    UnnecessarySelfAssignment
    UnnecessarySemicolon
    UnnecessaryStringInstantiation
    UnnecessarySubstring
    UnnecessaryTernaryExpression
    UnnecessaryTransientModifier


    ///////////////////////////////////
    // Set of "rulesets/design.xml" not at all included

    AbstractClassWithPublicConstructor
    AbstractClassWithoutAbstractMethod
    BooleanMethodReturnsNull
    CloneableWithoutClone
    CloseWithoutCloseable
    CompareToWithoutComparable
    ConstantsOnlyInterface
    EmptyMethodInAbstractClass
    FinalClassWithProtectedMember
    Instanceof
    LocaleSetDefault
    NestedForLoop
    PrivateFieldCouldBeFinal
    PublicInstanceField
    ReturnsNullInsteadOfEmptyArray
    ReturnsNullInsteadOfEmptyCollection
    SimpleDateFormatMissingLocale
    StatelessSingleton
    ToStringReturnsNull

}
