from django.db import models


# TODO: API'dan veri cekilecek.
class Currency(models.Model):
    from_currency = models.CharField(max_length=255)
    to_currency = models.CharField(max_length=255)
    exchange_rate = models.FloatField()
    id = models.IntegerField(primary_key=True)

    def __str__(self):
        return str(self.from_currency)+ "/"+str(self.to_currency) + ": " + str(self.exchange_rate)


# PEVAKXVH1TMR8SU9
