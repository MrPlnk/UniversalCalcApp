package ru.krivenchukartem.universalcalcapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.krivenchukartem.universalcalcapp.data.calculator.memory.InMemoryOperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.DefaultTokenTypeExtractor
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenParsingService
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenTypeExtractor
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.DefaultParsedTokenCompiler
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.ParsedTokenCompiler
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.RPN
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.FunctionRegistryProvider
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.TypeParserRegistry
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.ComplexParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.PSystemParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Tokenizer
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.UniversalTokenizer
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.useCases.ExtendTokenExpressionUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.ClearMemoryUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.GetMemoryByTypeUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.SaveMemoryUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides @Singleton
    fun provideTokenizer(): Tokenizer =
        UniversalTokenizer()

    @Provides @Singleton
    fun provideSaveMemoryUseCase(
        repo: OperationMemoryRepository,
        tokenizer: Tokenizer
    ): SaveMemoryUseCase =
        SaveMemoryUseCase(repo, tokenizer)

    @Provides @Singleton
    fun provideGetMemoryByTypeUseCase(
        repo: OperationMemoryRepository,
        extractor: TokenTypeExtractor
    ): GetMemoryByTypeUseCase =
        GetMemoryByTypeUseCase(repo, extractor)

    @Provides @Singleton
    fun provideClearMemoryUseCase(
        repo: OperationMemoryRepository
    ): ClearMemoryUseCase =
        ClearMemoryUseCase(repo)

    @Provides @Singleton
    fun provideExpressionTypeDetector(
        tokenizer: Tokenizer
    ): TokenTypeExtractor =
        DefaultTokenTypeExtractor(tokenizer)

    @Provides @Singleton
    fun provideParsedTokenCompiler(
        parsingService: TokenParsingService,
        rpn: RPN
    ): ParsedTokenCompiler = DefaultParsedTokenCompiler(parsingService, rpn)

    @Provides @Singleton
    fun provideOperationMemoryRepository(): OperationMemoryRepository =
        InMemoryOperationMemoryRepository()

    @Provides @Singleton
    fun provideTypeParserRegistry(): TypeParserRegistry =
        TypeParserRegistry(
            listOf(FractionalParser, ComplexParser, PSystemParser)
        )

    @Provides @Singleton
    fun provideRpn(): RPN = RPN()

    @Provides @Singleton
    fun provideFunctionRegistries(): FunctionRegistryProvider = FunctionRegistryProvider

    @Provides
    fun provideExtendTokenExpressionUseCase(
        repo: OperationMemoryRepository,
        saveMemoryUseCase: SaveMemoryUseCase
    ): ExtendTokenExpressionUseCase = ExtendTokenExpressionUseCase(repo, saveMemoryUseCase)
}
