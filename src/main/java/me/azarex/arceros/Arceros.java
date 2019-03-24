package me.azarex.arceros;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.neovisionaries.ws.client.WebSocketFactory;
import me.azarex.arceros.database.Database;
import me.azarex.arceros.database.impl.MySQL;
import me.azarex.arceros.scheduler.Scheduler;
import me.azarex.arceros.utility.FileUtility;
import me.azarex.arceros.utility.JacksonUtility;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.OnlineStatus;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Arceros {

    private final ShardManager shardManager;
    private final Scheduler scheduler;

    private static final String TOKEN = "NTU3NDM0NDAxMzc0NjAxMjE2.D3IOww.T9OnqyJ2FR9DpfOz8KFRkCG2dHk";

    private Arceros() throws LoginException, SQLException, ExecutionException, InterruptedException {
        scheduler = new Scheduler();
        shardManager = new DefaultShardManagerBuilder()
                .setToken(TOKEN)
                .setShardsTotal(2)
                .setStatus(OnlineStatus.ONLINE)
                .setAutoReconnect(true)
                .setWebsocketFactory(new WebSocketFactory().setVerifyHostname(false))
                .setHttpClient(new OkHttpClient())
                .setCallbackPool(scheduler.executor())
                .build();

        final Map<String, Object> configuration = JacksonUtility.fromJson(handleFiles());
        final Database database = new MySQL(configuration);

        database.execute(configuration.get("initial_sql_statement").toString()).get();

        CommandClientBuilder commandBuilder = new CommandClientBuilder()
                .setOwnerId("207649343874793474")
                .setPrefix("$a");

        shardManager.addEventListener(commandBuilder.build());

        swap(Arrays.asList(1, 2, 3, 4), 1, 2);
    }

    public <T> void swap(List<T> list, int indexOne, int indexTwo) {
        T temp = list.get(indexOne);
        list.set(indexOne, list.get(indexTwo));
        list.set(indexTwo, temp);
    }

    private Path handleFiles() {
        final InputStream resourceStream = getClass().getResourceAsStream("/configuration.json");
        final Path path = Paths.get(System.getProperty("user.dir"));

        return FileUtility.createAndOverwrite(resourceStream, path);
    }

    public static void main(String[] args) {
        try {
            new Arceros();
        } catch (LoginException | SQLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
