(define (not obj)
  (if obj #f #t))
  
(define (compose f g)
  (lambda (arg)
    (f (g arg))))
	
(define (list . rest) rest)
  
(define caar (compose car car))
(define cadr (compose car cdr)) 

(define cdar (compose cdr car))
(define cddr (compose cdr cdr)) 

(define caaar (compose car caar))
(define caadr (compose car cadr))
(define cadar (compose car cdar))
(define caddr (compose car cddr)) 

(define cdaar (compose cdr caar))
(define cdadr (compose cdr cadr))
(define cddar (compose cdr cdar))
(define cdddr (compose cdr cddr)) 

(define caaaar (compose caar caar))
(define caaadr (compose caar cadr))
(define caadar (compose caar cdar))
(define caaddr (compose caar cddr))
(define cadaar (compose cadr caar))
(define cadadr (compose cadr cadr))
(define caddar (compose cadr cdar))
(define cadddr (compose cadr cddr)) 

(define cdaaar (compose cdar caar))
(define cdaadr (compose cdar cadr))
(define cdadar (compose cdar cdar))
(define cdaddr (compose cdar cddr))
(define cddaar (compose cddr caar))
(define cddadr (compose cddr cadr))
(define cdddar (compose cddr cdar))
(define cddddr (compose cddr cddr))

(define (null? obj)
  (eqv? obj '()))

(define (boolean? obj)
  (if (eqv? obj #t)
      #t
	(if (eqv? obj #f)
	    #t
      #f)))

(define (and . rest)
  (if (null? rest)
      #t
	(if (car rest)
	    (apply and (cdr rest))
	  #f)))

(define (or . rest)
  (if (null? rest)
      #f
	(if (car rest)
	    #t
	  (apply or (cdr rest)))))