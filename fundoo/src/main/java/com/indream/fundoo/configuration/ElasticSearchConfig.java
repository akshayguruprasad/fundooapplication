/**
 * 
 */
package com.indream.fundoo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.indream.fundoo.noteservice.repository.NoteElasticRepository;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses=NoteElasticRepository.class)
public class ElasticSearchConfig {

}
