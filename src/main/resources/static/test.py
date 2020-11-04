# coding = utf-8

from selenium import webdriver
# from selenium.webdriver.chrome.options import Options
import time
from threading import Thread

chrome_driver = "C:\\Users\\Administrator\\AppData\\Local\\Programs\\Python\\Python38\\chromedriver.exe" 
BUY_URL = 'https://www.vmall.com/product/10086250925335.html'
LOGIN_URL = 'https://id1.cloud.huawei.com/CAS/portal/loginAuth.html?validated=true&themeName=red&service=https%3A%2F%2Fwww.vmall.com%2Faccount%2Fcaslogin%3Furl%3Dhttps%253A%252F%252Fwww.vmall.com%252F&loginChannel=26000000&reqClientType=26&lang=zh-cn'

def login():
    # options = Options()
    # options.add_argument('--headless')
    # options.add_argument('--disable-gpu')
    # driver = webdriver.Chrome(options=options, executable_path=chrome_driver)
    driver = webdriver.Chrome(executable_path=chrome_driver)
    driver.get(LOGIN_URL)

    # 扫码登录
    print('扫码登录...')

    # try:
    #     time.sleep(5)
    #     print('开始登录...')
    #     account = driver.find_element_by_xpath('//*[@ht="input_pwdlogin_account"]')
    #     account.send_keys("")
    #     time.sleep(1)
    #     password = driver.find_element_by_xpath('//*[@ht="input_pwdlogin_pwd"]')
    #     password.send_keys("")
    #     time.sleep(1)
    #     button = driver.find_element_by_css_selector('[class="hwid-btn hwid-btn-primary hwid-login-btn"]')
    #     button.click()
    # except:
    #     print('账号密码不能输入', sys.exc_info()[0])

    while True:
        time.sleep(3)
        if 'ANONYMITY_LOGIN_MOBILE' in driver.current_url and 'ANONYMITY_LOGIN_NAME' in driver.current_url:
            print('登录成功！')
            break
    buy(driver)
    

def buy(driver):
    driver.get(BUY_URL)





if __name__ == "__main__":
    login()