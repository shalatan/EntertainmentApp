package com.app.base

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<OutputParams, InputParams : UseCaseParams> :
    BaseUseCase<OutputParams, InputParams>() {
    abstract fun launch(params: InputParams): Flow<StateFullResult<OutputParams>>

}

