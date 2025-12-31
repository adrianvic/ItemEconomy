package io.github.adrianvic.itemeconomy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class UnrealConfig extends HashMap<String, Object> {
   private static final Yaml yaml = new Yaml();
   private final File file;

   public UnrealConfig(Object plugin, File dataDirectory, String filename) {
      this(plugin, dataDirectory.toPath(), filename, filename);
   }

   public UnrealConfig(Object plugin, File dataDirectory, String filename, String default_filename) {
      this(plugin, dataDirectory.toPath(), filename, default_filename);
   }

   public UnrealConfig(Object plugin, Path dataDirectory, String filename) {
      this(plugin, dataDirectory, filename, filename);
   }

   public UnrealConfig(Object plugin, Path dataDirectory, String filename, String default_filename) {
      this.file = Paths.get(dataDirectory.toFile().getPath(), filename).toFile();
      if (!dataDirectory.toFile().exists()) {
         dataDirectory.toFile().mkdir();
      }

      if (!this.file.exists()) {
         try {
            InputStream stream = plugin.getClass().getClassLoader().getResourceAsStream(default_filename);

            try {
               assert stream != null;

               Files.copy(stream, this.file.toPath(), new CopyOption[0]);
            } catch (Throwable var9) {
               if (stream != null) {
                  try {
                     stream.close();
                  } catch (Throwable var8) {
                     var9.addSuppressed(var8);
                  }
               }

               throw var9;
            }

            if (stream != null) {
               stream.close();
            }
         } catch (IOException var10) {
            throw new RuntimeException(var10);
         }
      }

      this.reload();
   }

   public void reload() {
      try {
         this.clear();
         this.putAll((Map)yaml.load(new FileInputStream(this.file)));
      } catch (FileNotFoundException var2) {
         var2.printStackTrace();
      }

   }

   public void save() {
      try {
         yaml.dump(this, new FileWriter(this.file));
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public Map<String, Object> clone() {
      return new HashMap(this);
   }
}
