from django.db import models
from django.contrib.auth.models import User


class Currency(models.Model):
    code = models.CharField(max_length=255)
    value = models.FloatField()

    def __str__(self):
        return str(self.code)+ " - "+str(self.value)


class Portfolio(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    id = models.IntegerField(primary_key=True)
    username = models.CharField(max_length=255,default="")
    currencies = models.ManyToManyField(Currency)

    def __str__(self):
        return str(User.username) + "'s Portfolio"

    def addCurrency(self, Currency):
        pass

    def deleteCurrency(self, Currency):
        pass

    def getPortfolio(self):
        pass
