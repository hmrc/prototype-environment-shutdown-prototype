package com.example.lambda.demo

import awscala.DefaultCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.regions.Regions
import com.amazonaws.services.autoscaling.AmazonAutoScalingClientBuilder
import com.amazonaws.services.autoscaling.model.UpdateAutoScalingGroupRequest
import com.amazonaws.services.elasticbeanstalk.model.{DescribeEnvironmentResourcesRequest, DescribeEnvironmentsRequest, DescribeEnvironmentsResult}
import com.amazonaws.services.elasticbeanstalk.{AWSElasticBeanstalk, AWSElasticBeanstalkClientBuilder}
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

import scala.collection.JavaConversions._

object ShutdownHandler extends RequestHandler[Object, String] {
  override def handleRequest(input: Object, context: Context): String = {
    val region = "eu-west-1"

    val credentialsProvider: DefaultCredentialsProvider = DefaultCredentialsProvider()
    val endpointConfiguration =
      new EndpointConfiguration(s"elasticbeanstalk.$region.amazonaws.com", Regions.fromName(region).getName)

    val ebClient: AWSElasticBeanstalk = AWSElasticBeanstalkClientBuilder.standard()
      .withCredentials(credentialsProvider)
      .withEndpointConfiguration(endpointConfiguration)
      .build()

    val describeRequest = new DescribeEnvironmentsRequest()

    val environmentsResult: DescribeEnvironmentsResult = ebClient.describeEnvironments(describeRequest)
    val environmentIds: Seq[String] = environmentsResult.getEnvironments.toList.map(_.getEnvironmentId)

    val environmentsScalingGroups = environmentIds flatMap { environmentId =>
      ebClient
        .describeEnvironmentResources(new DescribeEnvironmentResourcesRequest().withEnvironmentId(environmentId))
        .getEnvironmentResources
        .getAutoScalingGroups
        .toList
    }

    val scalingClient = AmazonAutoScalingClientBuilder.defaultClient()

    environmentsScalingGroups map { scalingGroup =>
      val scalingRequest = new UpdateAutoScalingGroupRequest()
        .withMaxSize(0)
        .withMinSize(0)
        .withAutoScalingGroupName(scalingGroup.getName)

      scalingClient.updateAutoScalingGroup(scalingRequest)
    }

    s"Number of environment scaling groups to modify found: ${environmentsScalingGroups.length}"

  }
}
