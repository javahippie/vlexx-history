function dateToDateString(date) {
  return date.getFullYear() + "-" + prependZero(date.getMonth() + 1) + "-" + prependZero(date.getDate());
}

function currentDateAsDateString() {
  return dateToDateString(new Date());
}

function prependZero(number) {
  if(number < 10) {
    return "0" + number;
  } else {
    return number;
  }
}
