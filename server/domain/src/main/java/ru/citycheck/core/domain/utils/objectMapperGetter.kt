package ru.citycheck.core.domain.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

val objectMapperGetter = ObjectMapper().apply {
    registerModule(KotlinModule.Builder().build())
}
