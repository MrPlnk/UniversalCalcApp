package ru.krivenchukartem.universalcalcapp.di

import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.krivenchukartem.universalcalcapp.data.calculator.memory.InMemoryOperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.RPN
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.FunctionRegistryProvider
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.TypeParserRegistry
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.ComplexParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.PSystemParser
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.useCases.ExtendTokenExpressionUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.SolveUniversalExpressionUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

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
        repo: OperationMemoryRepository
    ): ExtendTokenExpressionUseCase = ExtendTokenExpressionUseCase(repo)

}
