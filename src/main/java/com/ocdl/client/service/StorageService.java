package com.ocdl.client.service;

import java.io.File;

public interface StorageService {

  void uploadObject(String modelName, File file);
}
