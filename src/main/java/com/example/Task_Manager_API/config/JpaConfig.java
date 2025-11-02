package com.example.Task_Manager_API.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * ═══════════════════════════════════════════════════════════════════════════
 * JpaConfig - Конфигурация JPA Auditing
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * ЗАЧЕМ ЭТОТ КЛАСС:
 * Включает автоматическое заполнение полей с аннотациями:
 * - @CreatedDate → заполняется при создании
 * - @LastModifiedDate → обновляется при изменении
 *
 * БЕЗ ЭТОГО класса эти поля будут NULL!
 *
 * ═══════════════════════════════════════════════════════════════════════════
 */
@Configuration
@EnableJpaAuditing  // Включаем JPA Auditing
public class JpaConfig {
    // Класс пустой - аннотация @EnableJpaAuditing делает всю работу
}