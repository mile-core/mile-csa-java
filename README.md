# Requirements

1. Java 10

# Installation

Instructions for `gradle`, `maven` https://jitpack.io/#mile-core/mile-csa-java/

# Tests

`mvn test`

# Examples

## Chain configuration

```
    // default config. Mainnet network
    Config config = Config.custom().build();

    // Testnet with using several nodes to connect
    try {
        config = Config.custom()
                .setWebWalletUrl("https://wallet.testnet.mile.global")
                .setUseBalancing(true)
                .fetchApiUrlsFromWebWallet()
                .build();
    } catch (WebWalletCallException e) {
        e.printStackTrace();
    }

    // Testnet with using concrete node
    config = Config.custom()
            .setApiUrls(Arrays.asList("https://lotus000.testnet.mile.global"))
            .build();

    Chain chain = new Chain(config);
```

## Create wallet, check balance

```
    Wallet wallet = new Wallet(); // new random wallet
    wallet = new Wallet("secret-phrase"); // wallet from secret phrase

    System.out.println(wallet.getPublicKey() + " " + wallet.getPrivateKey());

    try {
        for (Balance balance : wallet.getState(chain).getBalances()) {
            System.out.println(balance);
        }
    } catch (ApiCallException e) {
        System.out.println(e.getMessage());
    }
```

## Transfer tokens

```
    Wallet destinationWallet = new Wallet("destination-secret-phrase");
    int assetCode = Asset.XDR;  // or Asset.MILE
    wallet.transfer(chain, destinationWallet, assetCode, new BigDecimal("0.01"));

    String destinationAddress = "2PThr9EhKLYJ7renYVogHqFXsLSp1UCAkcotU3HLNCrstJnhvo"; // aka public key
    String description = "test transfer";
    BigDecimal fee = new BigDecimal("0");
    wallet.transfer(chain, destinationAddress, assetCode, new BigDecimal("0.01"), description, fee);
```

## Do XDR emission

```
    wallet.emission(chain, Asset.MILE); // Stake all Miles and do XDR emission
```