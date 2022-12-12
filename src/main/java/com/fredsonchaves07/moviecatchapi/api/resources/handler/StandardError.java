package com.fredsonchaves07.moviecatchapi.api.resources.handler;

public record StandardError(int status, String type, String title, String detail, String instance) {

}
